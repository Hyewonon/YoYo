package com.yoyo.event.domain.event.dto;

import lombok.Builder;
import lombok.Getter;

public class EventDetailDTO {

    @Builder
    @Getter
    public static class Response {

        private Long eventId;
        private String title;
        private int totalReceiver;
        private long totalReceivedAmount;

        public static EventDetailDTO.Response of(Long eventId, String title, int totalReceiver,
                                                 long totalReceivedAmount) {
            return Response.builder()
                           .eventId(eventId)
                           .title(title)
                           .totalReceiver(totalReceiver)
                           .totalReceivedAmount(totalReceivedAmount)
                           .build();
        }
    }

}
