package com.yoyo.event.domain.event.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceConsumer {
    @KafkaListener(topics = "member-validation-result-topic", groupId = "event-service")
    public void handleValidationResult(String memberId, String isValid) {
        log.info("Received Message!!! MemberId : {}, isValid : {}", memberId, isValid);
        if (isValid.equals("true")) {
            log.info("IsValid. Can Create Event.");
        } else {
            log.info("Not Valid. Can't Create Event.");
        }
    }
}
