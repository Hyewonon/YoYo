package com.yoyo.member.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMemberValidationProducer {
    private final KafkaTemplate<Long, Boolean> kafkaTemplate;
    private final String RESULT_TOPIC = "member-validation-result-topic";

    public void sendValidationResult(Long memberId, boolean valid) {
        kafkaTemplate.send(RESULT_TOPIC, memberId, valid);
    }
}
