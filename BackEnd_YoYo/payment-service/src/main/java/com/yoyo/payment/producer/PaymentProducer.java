package com.yoyo.payment.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.PaymentDTO;
import com.yoyo.common.kafka.dto.ReceiverRequestDTO;
import com.yoyo.common.kafka.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;
    private final String NO_MEMBER_TOPIC = "payment-create-no-member-topic";
    private final String PAYMENT_SUCCESS_TOPIC = "payment-success";
    private final String PAYMENT_REGISTER_TOPIC = "payment-register";

    public void sendNoMemberPayment(PaymentDTO payment) {
        kafkaTemplate.send(NO_MEMBER_TOPIC, payment);
    }

    public void sendTransaction(TransactionDTO Transaction) {
        kafkaTemplate.send(PAYMENT_SUCCESS_TOPIC, Transaction);
    }

    public void sendRelation(TransactionDTO Transaction) {
        kafkaTemplate.send(PAYMENT_REGISTER_TOPIC, Transaction);
    }

    public void sendEventId(ReceiverRequestDTO request) {
        kafkaTemplate.send("eventId-receiverId", request);
    }
}
