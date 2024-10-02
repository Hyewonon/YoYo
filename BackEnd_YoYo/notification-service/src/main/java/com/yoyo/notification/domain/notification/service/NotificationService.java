package com.yoyo.notification.domain.notification.service;

import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.NotificationException;
import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.common.kafka.dto.NotificationCreateDTO;
import com.yoyo.common.kafka.dto.NotificationInfoDTO;
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
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationProducer producer;

    private final Map<String, CompletableFuture<MemberTagDTO>> tags = new ConcurrentHashMap<>();

    public void createNotification(NotificationCreateDTO request) {
        Notification notification = NotificationDTO.toEntity(request, LocalDateTime.now());
        // TODO: [FCM Server] Push알림 전송

        notificationRepository.save(notification);
    }

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

    public void completeMemberTag(MemberTagDTO tag) {
        String key = tag.getMemberId() + "&" + tag.getOppositeId();
        CompletableFuture<MemberTagDTO> future = tags.remove(key);
        if (future != null) {
            future.complete(tag);
        }
    }

    /**
     * 알림 단건 삭제
     * */
    public CommonResponse deleteNotification(Long memberId, Long notificationId) {
        if(!Objects.equals(findNotificationByNotificationId(notificationId).getReceiverId(),
                           memberId)) throw new NotificationException(ErrorCode.UNAUTHORIZED_NOTIFICATION);
        notificationRepository.deleteById(notificationId);
        return CommonResponse.of(true, "알림 삭제가 완료되었습니다.");
    }

    private Notification findNotificationByNotificationId(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException(ErrorCode.NOT_FOUND_NOTIFICATION));}
}
