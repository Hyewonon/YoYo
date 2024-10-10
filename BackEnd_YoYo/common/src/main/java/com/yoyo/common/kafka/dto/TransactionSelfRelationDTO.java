package com.yoyo.common.kafka.dto;

import com.yoyo.common.kafka.KafkaJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TransactionSelfRelationDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestToMember implements KafkaJson {

        private Long memberId;
        private Long oppositeId;
        private String oppositeName;
        private Long amount;
        private String transactionType;
        private String relationType;
        private String description;

        public static RequestToMember of(Long memberId, Long oppositeId, String oppositeName, Long amount,
                                         String transactionType, String relationType, String description) {
            return RequestToMember.builder()
                                  .memberId(memberId)
                                  .oppositeId(oppositeId)
                                  .oppositeName(oppositeName)
                                  .amount(amount)
                                  .transactionType(transactionType)
                                  .relationType(relationType)
                                  .description(description)
                                  .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ResponseFromMember implements KafkaJson {

        private Long memberId;
        private String memberName;
        private Long oppositeId;
        private String oppositeName;

        private String relationType;
        private String description;

        public static ResponseFromMember of(Long memberId, String memberName, Long oppositeId, String oppositeName
                , String relationType, String description) {
            return ResponseFromMember.builder()
                                     .memberId(memberId)
                                     .memberName(memberName)
                                     .oppositeId(oppositeId)
                                     .oppositeName(oppositeName)
                                     .relationType(relationType)
                                     .description(description)
                                     .build();
        }
    }
}
