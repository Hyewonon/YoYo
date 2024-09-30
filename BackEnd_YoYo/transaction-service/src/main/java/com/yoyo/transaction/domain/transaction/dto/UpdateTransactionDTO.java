package com.yoyo.transaction.domain.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UpdateTransactionDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        private String title;
        private String memo;
        private long amount;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private Long transactionId;
        private String title;
        private LocalDateTime updatedAt;
        private String memo;
        private long amount;
    }
}
