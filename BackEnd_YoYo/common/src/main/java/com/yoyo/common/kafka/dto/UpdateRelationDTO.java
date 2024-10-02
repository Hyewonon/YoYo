package com.yoyo.common.kafka.dto;

import com.yoyo.common.kafka.KafkaJson;
import lombok.*;

public class UpdateRelationDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request implements KafkaJson {
        private Long relationId;
        private Long memberId;
        private Long oppositeId;
        private String name;
        private String relationType;
        private String description;
        private long amount;
    }
}
