package com.yoyo.member.domain.member.consumer;

import com.yoyo.common.kafka.dto.PaymentDTO;
import com.yoyo.member.domain.member.producer.NoMemberProducer;
import com.yoyo.member.domain.member.service.NoMemberService;
import com.yoyo.member.domain.relation.service.RelationService;
import com.yoyo.member.entity.NoMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class NoMemberConsumer {

    private final String GROUP_ID = "member-service";
    private final String CREATE_NO_MEMBER_TOPIC = "payment-create-no-member-topic";
    private final NoMemberService noMemberService;
    private final RelationService relationService;
    private final NoMemberProducer producer;

    /*
     * 비회원 결제 시 NO_MEMBER 생성 요청
     */
    @KafkaListener(topics = CREATE_NO_MEMBER_TOPIC, groupId = GROUP_ID)
    public void registerNoMember(PaymentDTO request) {
        NoMember noMember = noMemberService.registerNoMember(request.getSenderName());
        String name = relationService.createPaymentRelation(noMember, request.getReceiverId(), request.getDescription(), request.getAmount());
        request.setSenderId(noMember.getMemberId());
        request.setReceiverName(name);
        producer.sendPayByNoMember(request);
    }
}
