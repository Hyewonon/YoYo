package com.yoyo.member.domain.relation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

public class RelationDTO {
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Response {
        private Long relationId;
        private Long oppositeId;
        private String oppositeName;
        private String relationType;
        private String description;
        private long totalReceivedAmount;
        private long totalSentAmount;
    }
}
