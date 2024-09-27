package com.yoyo.payment.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.PaymentDTO;
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

    public void sendNoMemberPayment(PaymentDTO payment) {

        kafkaTemplate.send(NO_MEMBER_TOPIC, payment);
    }

}
