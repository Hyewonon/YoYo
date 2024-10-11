package com.yoyo.common.kafka.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yoyo.common.kafka.KafkaJson;
import java.lang.reflect.Member;
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
public class MemberResponseDTO implements KafkaJson {

    private Long memberId;
    private String name;

    public static MemberResponseDTO of(Long memberId, String name) {
        return MemberResponseDTO.builder()
                                .memberId(memberId)
                                .name(name)
                                .build();
    }

}
