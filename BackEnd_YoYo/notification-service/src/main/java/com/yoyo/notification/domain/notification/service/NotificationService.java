package com.yoyo.notification.domain.notification.service;

import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.NotificationException;
import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.common.kafka.dto.NotificationCreateDTO;
import com.yoyo.common.kafka.dto.NotificationInfoDTO;
import com.yoyo.common.kafka.dto.PushTokenDTO;
import com.yoyo.notification.domain.notification.dto.ExpoNotificationDTO;
import com.yoyo.notification.domain.notification.dto.NotificationDTO;
import com.yoyo.notification.domain.notification.dto.NotificationUpdateDTO;
import com.yoyo.notification.domain.notification.producer.NotificationProducer;
import com.yoyo.notification.domain.notification.repository.NotificationRepository;
import com.yoyo.notification.entity.Notification;
import com.yoyo.notification.entity.NotificationType;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationProducer producer;
    private final RestTemplate restTemplate;
    private final String PayTitle = "YoYo \uD83D\uDCB0";
    private final String PayBody = "님이 마음을 전했습니다.";
    private final String EventTitle = "일정 \uD83D\uDCC5";
    private final String EventBody = "님이 일정을 등록했습니다.";
    private final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

    private final Map<String, CompletableFuture<MemberTagDTO>> tags = new ConcurrentHashMap<>();
    private final Map<Long, CompletableFuture<PushTokenDTO>> pushTokens = new ConcurrentHashMap<>();

    /**
     * 알림 생성
     */
    public void createNotification(NotificationCreateDTO request) {
        Notification notification = NotificationDTO.toEntity(request, LocalDateTime.now());
        producer.getPushToken(PushTokenDTO.of(notification.getReceiverId()));
        CompletableFuture<PushTokenDTO> future = new CompletableFuture<>();
        pushTokens.put(notification.getReceiverId(), future);
        String pushToken;
        try {
            pushToken = future.get(10, TimeUnit.SECONDS).getPushToken();
        } catch (Exception e) {
            throw new RuntimeException("Failed Kafka", e);
        }

        ExpoNotificationDTO.Request message;
        if (notification.getType().equals(NotificationType.PAY)) {
            message = ExpoNotificationDTO.Request.of(pushToken, PayTitle, notification.getName() + PayBody);
        } else {
            message = ExpoNotificationDTO.Request.of(pushToken, EventTitle, notification.getName() + EventBody);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ExpoNotificationDTO.Request> requestEntity = new HttpEntity<>(message, headers);

        try {
            ResponseEntity<?> response = restTemplate.exchange(EXPO_PUSH_URL, HttpMethod.POST, requestEntity,
                                                               ExpoNotificationDTO.Request.class);
        } catch (Exception e) {
            throw new NotificationException(ErrorCode.NOT_FOUND_PUSH_TOKEN);
        }

        notificationRepository.save(notification);
    }

    /**
     * 이벤트 알림 등록/미등록 응답
     */
    public NotificationUpdateDTO updateNotification(Long memberId, NotificationUpdateDTO request) {
        Notification notification = notificationRepository.findById(request.getNotificationId())
                                                          .orElseThrow(() -> new NotificationException(
                                                                  ErrorCode.NOT_FOUND_NOTIFICATION));

        notification.setIsRegister(true); // 응답 완료 처리
        if (request.getIsRegister()) {
            producer.createMemberEvent(NotificationInfoDTO.of(memberId, notification.getEventId()));
        }
        return NotificationUpdateDTO.of(notificationRepository.save(notification));
    }

    /**
     * 알림 목록 조회
     */
    public List<NotificationDTO.Response> getNotificationList(Long memberId, String type) {
        List<Notification> notifications = notificationRepository.findAllByReceiverIdAndTypeAndIsRegisterFalseOrderByCreatedAtDesc(
                memberId,
                NotificationType.valueOf(
                        type.toUpperCase()));
        return notifications.stream()
                            .map(notification -> {
                                // Tag & Description Get
                                MemberTagDTO message = new MemberTagDTO(memberId, notification.getSenderId());
                                producer.getFriendTag(message);
                                CompletableFuture<MemberTagDTO> future = new CompletableFuture<>();
                                String key = memberId + "&" + notification.getSenderId();
                                tags.put(key, future);
                                String tag;
                                String description;
                                try {
                                    message = future.get(10, TimeUnit.SECONDS);
                                    tag = message.getTag();
                                    description = message.getDescription();
                                } catch (Exception e) {
                                    throw new RuntimeException("Failed Kafka", e);
                                }
                                return NotificationDTO.Response.of(notification, tag, description);
                            })
                            .collect(Collectors.toList());
    }

    /**
     * 알림 단건 삭제
     */
    public CommonResponse deleteNotification(Long memberId, Long notificationId) {
        if (!Objects.equals(findNotificationByNotificationId(notificationId).getReceiverId(),
                            memberId)) {
            throw new NotificationException(ErrorCode.UNAUTHORIZED_NOTIFICATION);
        }
        notificationRepository.deleteById(notificationId);
        return CommonResponse.of(true, "알림 삭제가 완료되었습니다.");
    }

    public void completeMemberTag(MemberTagDTO tag) {
        String key = tag.getMemberId() + "&" + tag.getOppositeId();
        CompletableFuture<MemberTagDTO> future = tags.remove(key);
        if (future != null) {
            future.complete(tag);
        }
    }

    public void completePushToken(PushTokenDTO token) {
        CompletableFuture<PushTokenDTO> future = pushTokens.remove(token.getMemberId());
        if (future != null) {
            future.complete(token);
        }
    }

    private Notification findNotificationByNotificationId(Long notificationId) {
        return notificationRepository.findById(notificationId)
                                     .orElseThrow(() -> new NotificationException(ErrorCode.NOT_FOUND_NOTIFICATION));
    }
}
