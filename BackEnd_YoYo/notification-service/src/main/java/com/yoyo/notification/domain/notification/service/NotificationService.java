package com.yoyo.notification.domain.notification.service;

import com.yoyo.notification.domain.notification.dto.NotificationCreateDTO;
import com.yoyo.notification.domain.notification.dto.NotificationDTO;
import com.yoyo.notification.domain.notification.repository.NotificationRepository;
import com.yoyo.notification.entity.Notification;
import com.yoyo.notification.entity.NotificationType;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

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
                                String tag = "친구"; // 임시 태그
                                return NotificationDTO.Response.of(notification, tag);
                            })
                            .collect(Collectors.toList());
    }
}
