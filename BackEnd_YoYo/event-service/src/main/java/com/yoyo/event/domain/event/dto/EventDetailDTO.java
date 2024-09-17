package com.yoyo.event.domain.event.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class EventDetailDTO {

    @Builder
    @Getter
    public static class Response {

        private Long eventId;
        private Long totalReceiver;
        private Long totalReceivedAmount;
        private List<TransactionDTO.Response> transactions;

        public static EventDetailDTO.Response of(Long eventId, Long totalReceiver, Long totalReceivedAmount,
                                                 List<TransactionDTO.Response> transactions) {
            return Response.builder()
                           .eventId(eventId)
                           .totalReceiver(totalReceiver)
                           .totalReceivedAmount(totalReceivedAmount)
                           .transactions(transactions)
                           .build();
        }
    }

}
