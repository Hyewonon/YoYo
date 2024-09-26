package com.yoyo.notification.domain.notification.service;

import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.notification.domain.notification.dto.NotificationCreateDTO;
import com.yoyo.notification.domain.notification.dto.NotificationDTO;
import com.yoyo.notification.domain.notification.producer.NotificationProducer;
import com.yoyo.notification.domain.notification.repository.NotificationRepository;
import com.yoyo.notification.entity.Notification;
import com.yoyo.notification.entity.NotificationType;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    public NotificationCreateDTO.Response createNotification(Long memberId, NotificationCreateDTO.Request request) {
        Notification notification = NotificationCreateDTO.Request.toEntity(request, memberId, LocalDateTime.now());

        // TODO: [FCM Server] Push알림 전송

        return NotificationCreateDTO.Response.of(notificationRepository.save(notification));
    }

    public List<NotificationDTO.Response> getNotificationList(Long memberId, String type) {
        List<Notification> notifications = notificationRepository.findAllByReceiverIdAndTypeOrderByCreatedAtDesc(
                memberId,
                NotificationType.valueOf(
                        type.toUpperCase()));
        return notifications.stream()
                            .map(notification -> {
                                // TODO : [Relation] 친구 태그 Get
                                MemberTagDTO message = new MemberTagDTO(memberId, notification.getSenderId());
                                producer.getFriendTag(message);
                                CompletableFuture<MemberTagDTO> future = new CompletableFuture<>();
                                String key = memberId+"&"+notification.getSenderId();
                                tags.put(key, future);
                                String tag;
                                try{
                                    tag = future.get(10, TimeUnit.SECONDS).getTag();
                                } catch (Exception e){
                                    throw new RuntimeException("Failed Kafka", e);
                                }
                                return NotificationDTO.Response.of(notification, tag);
                            })
                            .collect(Collectors.toList());
    }

    public void completeMemberTag(MemberTagDTO tag) {
        String key = tag.getMemberId()+"&"+tag.getOppositeId();
        CompletableFuture<MemberTagDTO> future = tags.remove(key);
        if (future != null) {
            future.complete(tag);
        }
    }
}
