package com.yoyo.common.kafka.dto;

import com.yoyo.common.kafka.KafkaJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberTagDTO implements KafkaJson {

    private Long memberId;
    private Long oppositeId;
    private String tag;

    public MemberTagDTO(Long memberId, Long oppositeId) {
        this.memberId = memberId;
        this.oppositeId = oppositeId;
    }
}
