package com.yoyo.transaction.adapter.out.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionProducer {
    private final KafkaTemplate<String, TransactionSummaryResponse> kafkaTemplate;

    public void sendTransactionSummary(TransactionSummaryResponse summary) {
        kafkaTemplate.send("transaction-summary-topic", summary);
    }
}
