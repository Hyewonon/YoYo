package com.yoyo.payment.service;

import com.yoyo.payment.dto.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuccessService {
    private final KafkaTemplate<String, Transaction> kafkaTemplate;
    private final String PAYMENT_SUCCESS_TOPIC = "payment-success";
    private final String PAYMENT_REGISTER_TOPIC = "payment-register";
    public void sendTransaction(Transaction Transaction) {
        kafkaTemplate.send(PAYMENT_SUCCESS_TOPIC, Transaction);
    }
    public void sendRelation(Transaction Transaction) {
        kafkaTemplate.send(PAYMENT_REGISTER_TOPIC, Transaction);
    }
}