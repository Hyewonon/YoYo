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
public class PayInfoRequestToMemberDTO implements KafkaJson {
    private Long senderId;
    private Long receiverId;
    private Long amount;

    public static PayInfoRequestToMemberDTO of(Long senderId, Long receiverId, Long amount) {
        return PayInfoRequestToMemberDTO.builder()
                              .senderId(senderId)
                              .receiverId(receiverId)
                              .amount(amount)
                              .build();
    }
}
