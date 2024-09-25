package com.yoyo.eureka.domain.event.dto;

import java.time.LocalDateTime;
import lombok.Getter;

public class TransactionDTO {

    @Getter
    public static class Response{
        private Long transactionId;
        private Long senderId;
        private Long receiverId;
        private Long eventId;
        private boolean isSenderMember;
        private Long amount;
        private LocalDateTime createdAt;
        private String memo;
    }
}
