package com.yoyo.member.adapter.out.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.EventMemberResponseDTO;
import com.yoyo.common.kafka.dto.PayInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberProducer {

    private final String UPDATE_TRANSACTION_TOPIC = "pay-update-transaction-topic";

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;

    public void sendMemberName(EventMemberResponseDTO response) {
        kafkaTemplate.send("member-name-topic", response);
    }

    public void sendPayInfoToTransaction(PayInfoDTO.RequestToTransaction request) {
        kafkaTemplate.send(UPDATE_TRANSACTION_TOPIC, request);
    }
}
