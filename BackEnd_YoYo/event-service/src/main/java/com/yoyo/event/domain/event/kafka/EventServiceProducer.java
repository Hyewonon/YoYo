package com.yoyo.event.domain.event.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceProducer {
    private final KafkaTemplate<String, Long> kafkaTemplate;
    private final String MEMBER_TOPIC = "member-validation-topic";
    public void sendMemberId(Long memberId) {
        kafkaTemplate.send(MEMBER_TOPIC, memberId);
    }
}
