package com.yoyo.member.adapter.out.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.EventMemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberProducer {

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;

    public void sendMemberName(EventMemberResponseDTO response) {
        kafkaTemplate.send("member-name-topic", response);
    }
}
