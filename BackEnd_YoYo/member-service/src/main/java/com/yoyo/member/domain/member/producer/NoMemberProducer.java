package com.yoyo.member.domain.member.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.EventMemberResponseDTO;
import com.yoyo.common.kafka.dto.PayInfoDTO;
import com.yoyo.common.kafka.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoMemberProducer {

    private final String UPDATE_YOYO_PAY_BY_NO_MEMBER = "update-yoyo-pay-by-no-member";

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;

    public void sendPayByNoMember(PaymentDTO request) {
        kafkaTemplate.send(UPDATE_YOYO_PAY_BY_NO_MEMBER, request);
    }
}
