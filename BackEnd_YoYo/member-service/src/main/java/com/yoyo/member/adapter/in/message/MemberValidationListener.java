package com.yoyo.member.adapter.in.message;

import com.yoyo.member.adapter.out.KafkaMemberValidationProducer;
import com.yoyo.member.application.port.in.ValidateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidationListener {
    private final ValidateMemberUseCase validateMemberUseCase;
    private final KafkaMemberValidationProducer kafkaProducer;
    @KafkaListener(topics = "member-validation-topic",groupId = "member-service")
    public void handleAccountCreated(String memberId) {
        Long id = Long.parseLong(memberId);
        boolean valid = validateMemberUseCase.validateMember(id);
        kafkaProducer.sendValidationResult(id, valid);
    }
}
