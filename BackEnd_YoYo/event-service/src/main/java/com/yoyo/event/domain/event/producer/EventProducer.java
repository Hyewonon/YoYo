package com.yoyo.event.domain.event.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.TransactionRequestDTO;
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
    public void sendEventId(TransactionRequestDTO event) {
        log.info("send event id : {}", event.getEventId());
        kafkaTemplate.send(TRANSACTION_TOPIC, event);
    }
}
