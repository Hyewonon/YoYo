package com.yoyo.common.kafka.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yoyo.common.kafka.KafkaJson;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
@Builder
public class RelationResponseDTO implements KafkaJson {

    private Long memberId;
    private List<Long> relationIds;

    public static RelationResponseDTO of(Long memberId, List<Long> relationIds) {
        return RelationResponseDTO.builder()
                                  .memberId(memberId)
                                  .relationIds(relationIds)
                                  .build();
    }

}
