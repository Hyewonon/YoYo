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
@Builder
public class PushTokenDTO implements KafkaJson {

    private Long memberId;
    private String pushToken;

    public static PushTokenDTO of(Long memberId){
        return PushTokenDTO.builder()
                           .memberId(memberId)
                           .build();
    }

}
