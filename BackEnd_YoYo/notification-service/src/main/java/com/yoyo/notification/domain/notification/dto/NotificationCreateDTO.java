package com.yoyo.notification.domain.notification.dto;

import com.yoyo.notification.entity.Notification;
import com.yoyo.notification.entity.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

public class NotificationCreateDTO {

    @Getter
    public static class Request {

        private Long memberId;
        private String name;
        private Long eventId;
        private String title;
        private String type;

        public static Notification toEntity(NotificationCreateDTO.Request request, Long memberId, LocalDateTime now) {
            return Notification.builder()
                               .senderId(request.getMemberId())
                               .name(request.getName())
                               .receiverId(memberId)
                               .eventId(request.getEventId())
                               .title(request.getTitle())
                               .createdAt(now)
                               .type(NotificationType.valueOf(request.getType().toUpperCase()))
                               .build();
        }
    }

    @Getter
    @Builder
    public static class Response {

        private Long notificationId;
        private Long memberId;
        private String name;
        private String tag;
        private Long eventId;
        private String title;
        private LocalDateTime createdAt;

        public static Response of(Notification notification) {
            return Response.builder()
                           .notificationId(notification.getId())
                           .memberId(notification.getSenderId())
                           .name(notification.getName())
                           .eventId(notification.getEventId())
                           .title(notification.getTitle())
                           .createdAt(notification.getCreatedAt())
                           .build();
        }
    }
}
