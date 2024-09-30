package com.yoyo.common.kafka.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yoyo.common.kafka.KafkaJson;
import lombok.*;

public class FindDescriptionDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonSerialize
    @JsonDeserialize
    @Builder
    public static class Request implements KafkaJson {
        private Long memberId;
        private Long oppositeId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonSerialize
    @JsonDeserialize
    @Builder
    public static class Response implements KafkaJson {
        private String description;
    }
}
