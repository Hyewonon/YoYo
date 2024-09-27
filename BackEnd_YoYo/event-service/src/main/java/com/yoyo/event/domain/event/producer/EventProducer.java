package com.yoyo.event.domain.event.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.AmountRequestDTO;
import com.yoyo.common.kafka.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventProducer {

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;
    private final String TRANSACTION_TOPIC = "transaction-topic";
    private final String MEMBER_TOPIC = "event-member-name-topic";

    public void sendEventId(AmountRequestDTO event) {
        log.info("send event id : {}", event.getEventId());
        kafkaTemplate.send(TRANSACTION_TOPIC, event);
    }

    public void getMemberName(MemberRequestDTO event) {
        log.info("SEND MEMBER ID FOR EVENT");
        kafkaTemplate.send(MEMBER_TOPIC, event);
    }

}
