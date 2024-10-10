package com.yoyo.member.domain.relation.dto;

import lombok.*;

public class FindRelationDTO {
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @Builder
    @Getter
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
