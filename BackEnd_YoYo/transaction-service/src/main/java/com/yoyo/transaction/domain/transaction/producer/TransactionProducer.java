package com.yoyo.transaction.domain.transaction.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionProducer {
    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;

    private final String CREATE_TRANSACTION_SELF_RELATION_TOPIC = "create-transaction-self-relation-topic";

    public void sendTransactionSummary(AmountResponseDTO responseDTO) {
        kafkaTemplate.send("transaction-summary-topic", responseDTO);
    }

    public void sendRelation(IncreaseAmountDTO amount) {
        kafkaTemplate.send("transaction-register-topic", amount);
    }

    public void sendRelationRequest(RelationDTO.Request request) {
        kafkaTemplate.send("relation-request-topic", request);
    }

    /**
     * 요요 거래 직접등록시 친구관계 수정
     * Transaction -> Relation
     */
    public void sendSelfTransactionRelation(TransactionSelfRelationDTO.RequestToMember request) {
        kafkaTemplate.send(CREATE_TRANSACTION_SELF_RELATION_TOPIC, request);
    }

    public void sendRelationDescription(FindDescriptionDTO.Request request) {
        kafkaTemplate.send("relation-description-topic", request);
    }
}
