package com.yoyo.member.adapter.in.message;

import com.yoyo.member.adapter.out.KafkaMemberValidationProducer;
import com.yoyo.member.application.port.in.event.ValidateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidationListener {
    private final ValidateMemberUseCase validateMemberUseCase;
    private final KafkaMemberValidationProducer kafkaProducer;
    @KafkaListener(topics = "member-validation-topic",groupId = "member-service")
    public void handleAccountCreated(Long memberId) {
        boolean valid = validateMemberUseCase.validateMember(memberId);
        kafkaProducer.sendValidationResult(memberId, valid);
    }
}
