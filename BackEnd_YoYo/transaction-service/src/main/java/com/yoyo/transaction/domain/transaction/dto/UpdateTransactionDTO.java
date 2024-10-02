package com.yoyo.transaction.domain.transaction.dto;

import com.yoyo.transaction.entity.RelationType;
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
        private Long relationId;
        private Long oppositeId;
        private String name;
        private RelationType relationType;
        private String description;
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
        private String description;
        private long amount;
    }
}
