package com.yoyo.notification.domain.notification.dto;

import com.yoyo.common.kafka.dto.NotificationCreateDTO;
import com.yoyo.notification.entity.Notification;
import com.yoyo.notification.entity.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class NotificationDTO {

    public static Notification toEntity(NotificationCreateDTO request, LocalDateTime now) {
        return Notification.builder()
                           .senderId(request.getSenderId())
                           .name(request.getName())
                           .receiverId(request.getReceiverId())
                           .eventId(request.getEventId())
                           .title(request.getTitle())
                           .createdAt(now)
                           .type(NotificationType.valueOf(request.getType().toUpperCase()))
                           .isRegister(false)
                           .build();
    }

    @Getter
    @Builder
    public static class Response {

        private Long notificationId;
        private Long memberId;
        private String name;
        private String tag;
        private String description;
        private Long eventId;
        private String title;
        private LocalDateTime createdAt;
        private String type;

        public static Response of(Notification notification, String tag, String description) {
            return Response.builder()
                           .notificationId(notification.getId())
                           .memberId(notification.getSenderId())
                           .name(notification.getName())
                           .tag(tag)
                           .description(description)
                           .eventId(notification.getEventId())
                           .title(notification.getTitle())
                           .createdAt(notification.getCreatedAt())
                           .type(notification.getType().toString())
                           .build();
        }
    }

}
