package com.yoyo.transaction.domain.transaction.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionProducer {
    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;

    private final String CREATE_TRANSACTION_SELF_RELATION_TOPIC = "create-transaction-self-relation-topic";
    private final String SEND_EVENTID_EVENT_TOPIC = "send-event-id-event-topic";


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

    public void sendRelationUpdate(UpdateRelationDTO.Request request) {
        kafkaTemplate.send("update-relation-topic", request);
    }

    /**
     * event Id로 event Name을 불러옴
     * */
    public void getEventNameByEventId(Long eventId) {
        kafkaTemplate.send(SEND_EVENTID_EVENT_TOPIC, EventRequestDTO.of(eventId));
    }

    public void sendOCRRegister(OcrRegister.OcrList ocrList) {
        kafkaTemplate.send("ocr-register", ocrList);
    }
}
