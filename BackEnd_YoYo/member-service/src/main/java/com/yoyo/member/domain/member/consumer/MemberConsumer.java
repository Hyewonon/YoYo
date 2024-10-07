package com.yoyo.member.domain.member.consumer;

import com.yoyo.common.kafka.dto.MemberRequestDTO;
import com.yoyo.common.kafka.dto.MemberResponseDTO;
import com.yoyo.common.kafka.dto.PayInfoDTO;
import com.yoyo.common.kafka.dto.PushTokenDTO;
import com.yoyo.member.domain.member.producer.MemberProducer;
import com.yoyo.member.domain.member.service.MemberService;
import com.yoyo.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class MemberConsumer {

    private final String GROUP_ID = "member-service";
    private final String UPDATE_TRANSACTION_MEMBER_TOPIC = "pay-update-transaction-member-topic";
    private final String GET_PAY_MEMBER_NAME = "pay-get-pay-member-name";
    private final String GET_MEMBER_PUSH_TOKEN = "get-member-push-token";

    private final MemberService memberService;
    private final MemberProducer memberProducer;

    /**
     * 회원 이름 조회
     * */
    @KafkaListener(topics = "event-member-name-topic")
    public void getEventMemberName(MemberRequestDTO message) {
        Member member = memberService.findMemberById(message.getMemberId());
        memberProducer.sendMemberNameToEvent(MemberResponseDTO.of(member.getMemberId(), member.getName()));
    }

    @KafkaListener(topics = GET_PAY_MEMBER_NAME)
    public void getPayMemberName(MemberRequestDTO message) {
        Member member = memberService.findMemberById(message.getMemberId());
        memberProducer.sendMemberNameToPay(MemberResponseDTO.of(member.getMemberId(), member.getName()));
    }
    /**
     * * 페이 송금 시 거래 내역 저장을 위해 발신자 이름 요청
     * @param : reqeust 페이 송금 거래내역 저장 정보
     * */
    @KafkaListener(topics = UPDATE_TRANSACTION_MEMBER_TOPIC, groupId = GROUP_ID)
    public void getMemberNameForPay(PayInfoDTO.RequestToTransaction request) {
        Member sender = memberService.findMemberById(request.getSenderId());
        request.setSenderName(sender.getName()); // 발신자 이름 저장
        // 거래내역 저장 요청
        memberProducer.sendPayInfoToTransaction(request);
    }

    @KafkaListener(topics = GET_MEMBER_PUSH_TOKEN)
    public void getPayMemberName(PushTokenDTO request) {
        Member member = memberService.findMemberById(request.getMemberId());
        request.setPushToken(member.getFcmToken());
        memberProducer.sendPushToken(request);
    }
}
