package com.yoyo.common.kafka.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yoyo.common.kafka.KafkaJson;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
@Builder
public class IncreaseAmountDTO implements KafkaJson {
    private Long memberId;
    private Long oppositeId;
    private long amount;
    private boolean send;
}
