package com.yoyo.notification.domain.notification.dto;

import com.yoyo.notification.entity.Notification;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class NotificationDTO {

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
