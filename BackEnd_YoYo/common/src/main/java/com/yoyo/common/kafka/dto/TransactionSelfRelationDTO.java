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

        public static RequestToMember of(Long memberId, Long oppositeId, String oppositeName, Long amount,
                                         String transactionType, String relationType) {
            return TransactionSelfRelationDTO.RequestToMember.builder()
                                                             .memberId(memberId)
                                                             .oppositeId(oppositeId)
                                                             .oppositeName(oppositeName)
                                                             .amount(amount)
                                                             .transactionType(transactionType)
                                                             .relationType(relationType)
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
    }
}
