package com.yoyo.notification.domain.notification.dto;

import lombok.Builder;
import lombok.Getter;

public class ExpoNotificationDTO {

    @Getter
    @Builder
    public static class Request {

        private String to;
        private String title;
        private String body;

        public static Request of(String to, String title, String body) {
            return Request.builder()
                          .to(to)
                          .title(title)
                          .body(body)
                          .build();
        }
    }

}
