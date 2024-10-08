package com.yoyo.banking.domain.account.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.MemberRequestDTO;
import com.yoyo.common.kafka.dto.NotificationCreateDTO;
import com.yoyo.common.kafka.dto.PayInfoRequestToMemberDTO;
import com.yoyo.common.kafka.dto.PayInfoRequestToTransactionDTO;
import com.yoyo.common.kafka.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayProducer {

    private final String UPDATE_RELATION_TOPIC = "pay-update-relation-topic";
    private final String UPDATE_TRANSACTION_MEMBER_TOPIC = "pay-update-transaction-topic";
    private final String UPDATE_TRANSACTION_NO_MEMBER_TOPIC = "pay-update-transaction-no-member-topic";
    private final String GET_PAY_MEMBER_NAME = "pay-get-pay-member-name";
    private final String CREATE_PAY_NOTIFICATION = "create-notification";

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;

    public void sendPayInfoToMember(PayInfoRequestToMemberDTO request) {
        kafkaTemplate.send(UPDATE_RELATION_TOPIC, request);
    }

    public void sendPayInfoToTransaction(PayInfoRequestToTransactionDTO request) {
        kafkaTemplate.send(UPDATE_TRANSACTION_MEMBER_TOPIC, request);
    }

    public void sendPaymentInfoToTransaction(PaymentDTO request) {
        kafkaTemplate.send(UPDATE_TRANSACTION_NO_MEMBER_TOPIC, request);
    }

    public void sendPayToMemberForName(Long memberId) {
        kafkaTemplate.send(GET_PAY_MEMBER_NAME, MemberRequestDTO.of(memberId));
    }

    public void sendPayNotification(NotificationCreateDTO request) {
        kafkaTemplate.send(CREATE_PAY_NOTIFICATION, request);
    }
}
