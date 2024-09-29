package com.yoyo.member.domain.member.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.MemberRequestDTO;
import com.yoyo.common.kafka.dto.MemberResponseDTO;
import com.yoyo.common.kafka.dto.PayInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberProducer {

    private final String UPDATE_TRANSACTION_TOPIC = "pay-update-transaction-topic";
    private final String MEMBER_NAME_TO_PAY_TOPIC = "member-name-to-pay-topic";
    private final String USER_KEY_TO_BANKING_TOPIC = "user-key-to-banking-topic";

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;

    public void sendMemberNameToEvent(MemberResponseDTO response) {
        kafkaTemplate.send("member-name-topic", response);
    }
    public void sendMemberNameToPay(MemberResponseDTO response) {
        kafkaTemplate.send(MEMBER_NAME_TO_PAY_TOPIC, response);
    }

    public void sendPayInfoToTransaction(PayInfoDTO.RequestToTransaction request) {
        kafkaTemplate.send(UPDATE_TRANSACTION_TOPIC, request);
    }

    public void sendBankingUserkey(Long memberId) {
        kafkaTemplate.send(USER_KEY_TO_BANKING_TOPIC, MemberRequestDTO.of(memberId));
    }
}
