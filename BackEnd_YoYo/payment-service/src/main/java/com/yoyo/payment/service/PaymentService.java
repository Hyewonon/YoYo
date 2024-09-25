package com.yoyo.payment.service;

import com.yoyo.common.kafka.dto.TransactionDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final KafkaTemplate<String, TransactionDTO> kafkaTemplate;
    private final String PAYMENT_SUCCESS_TOPIC = "payment-success";
    private final String PAYMENT_REGISTER_TOPIC = "payment-register";
    public void sendTransaction(TransactionDTO Transaction) {
        kafkaTemplate.send(PAYMENT_SUCCESS_TOPIC, Transaction);
    }
    public void sendRelation(TransactionDTO Transaction) {
        kafkaTemplate.send(PAYMENT_REGISTER_TOPIC, Transaction);
    }
}