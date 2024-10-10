package com.yoyo.transaction.domain.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class FindTransactionDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RelationSendResponse{
        private Long transactionId;
        private String receiveName;
        private String relationType;
        private String memo;
        private long amount;
        private LocalDateTime time;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RelationReceiveResponse{
        private Long transactionId;
        private String senderName;
        private String relationType;
        private String memo;
        private long amount;
        private LocalDateTime time;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private Long transactionId;
        private Long oppositeId;
        private String senderName;
        private String relationType;
        private String memo;
        private String eventName;
        private long amount;
        private LocalDateTime time;
    }
}
