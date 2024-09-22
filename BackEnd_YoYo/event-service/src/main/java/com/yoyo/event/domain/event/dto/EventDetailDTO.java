package com.yoyo.event.domain.event.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class EventDetailDTO {

    @Builder
    @Getter
    public static class Response {

        private Long eventId;
        private int totalReceiver;
        private long totalReceivedAmount;

        public static EventDetailDTO.Response of(Long eventId, int totalReceiver, long totalReceivedAmount) {
            return Response.builder()
                           .eventId(eventId)
                           .totalReceiver(totalReceiver)
                           .totalReceivedAmount(totalReceivedAmount)
                           .build();
        }
    }

}
