package com.yoyo.payment.service;

import com.yoyo.payment.dto.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuccessService {
    private final KafkaTemplate<String, Transaction> kafkaTemplate;
    private final String PAYMENT_TOPIC = "payment-success";
    public void sendTransaction(Transaction Transaction) {
        kafkaTemplate.send(PAYMENT_TOPIC, Transaction);
    }
}