package com.yoyo.banking.domain.account.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.PayInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayProducer {

    private final String UPDATE_RELATION_TOPIC = "pay-update-relation-topic";
    private final String UPDATE_TRANSACTION_MEMBER_TOPIC = "pay-update-transaction-member-topic";

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;

    public void sendPayInfoToMember(PayInfoDTO.RequestToMember request) {
        kafkaTemplate.send(UPDATE_RELATION_TOPIC, request);
    }

    public void sendPayInfoToTransaction(PayInfoDTO.RequestToTransaction request) {
        kafkaTemplate.send(UPDATE_TRANSACTION_MEMBER_TOPIC, request);
    }
}
