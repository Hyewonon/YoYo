package com.yoyo.event.domain.event.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventProducer {
    private final KafkaTemplate<String, TransactionSummaryRequest> kafkaTemplate;
    private final String TRANSACTION_TOPIC = "transaction-topic";
    public void sendEventId(TransactionSummaryRequest event) {
        kafkaTemplate.send(TRANSACTION_TOPIC, event);
    }
}
