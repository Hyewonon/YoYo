package com.yoyo.eureka.domain.member_event.dto;

import com.yoyo.eureka.domain.event.dto.TransactionDTO;
import com.yoyo.eureka.entity.Event;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class MemberEventTransactionDTO {

    @Getter
    @Builder
    public static class Response {

        private Long eventId;
        private String title;
        private String location;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
        private String name;
        private Long totalSentAmount;
        private Long totalReceivedAmount;
        private List<TransactionDTO.Response> transactions;

        public static MemberEventTransactionDTO.Response of(String name, Event event,
                                                            Long totalSentAmount, Long totalReceivedAmount,
                                                            List<TransactionDTO.Response> transactions) {
            return Response.builder()
                           .eventId(event.getId())
                           .title(event.getTitle())
                           .location(event.getLocation())
                           .startAt(event.getStartAt())
                           .endAt(event.getEndAt())
                           .name(name)
                           .totalSentAmount(totalSentAmount)
                           .totalReceivedAmount(totalReceivedAmount)
                           .transactions(transactions)
                           .build();
        }

    }
}
