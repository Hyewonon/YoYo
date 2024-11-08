package com.yoyo.common.kafka.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yoyo.common.kafka.KafkaJson;
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
public class MemberRequestDTO implements KafkaJson {

    private Long memberId;

    public static MemberRequestDTO of(Long memberId){
        return MemberRequestDTO.builder()
                               .memberId(memberId)
                               .build();
    }

}
