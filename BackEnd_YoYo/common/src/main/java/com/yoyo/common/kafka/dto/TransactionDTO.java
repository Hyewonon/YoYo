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
public class TransactionDTO implements KafkaJson {
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Long eventId;
    private String title;
    private boolean isMember;
    private long amount;
    private String memo;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonSerialize
    @JsonDeserialize
    @Builder
    @ToString
    public static class MatchRelation implements KafkaJson {
        private Long memberId;
        private RelationType relationType;
        private String name;
        private Long amount;
        private String description;
        private int relationStatus;
    }
}
