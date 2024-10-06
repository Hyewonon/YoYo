package com.yoyo.common.kafka.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yoyo.common.kafka.KafkaJson;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OcrRegister {
    private Long memberId;
    private Long oppositeId;
    private long amount;
    private String memberName;
    private String oppositeName;
    private String description;
    private String relationType;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonSerialize
    @JsonDeserialize
    @Builder
    public static class OcrList implements KafkaJson {
        private List<OcrRegister> ocrList;
    }
}
