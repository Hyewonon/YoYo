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
public class CreateTransactionDTO implements KafkaJson {
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Long eventId;
    private String title;
    private boolean isMember;
    private int amount;
    private String memo;
}
