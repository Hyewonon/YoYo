package com.yoyo.member.domain.member.consumer;

import com.yoyo.common.kafka.dto.EventMemberRequestDTO;
import com.yoyo.common.kafka.dto.EventMemberResponseDTO;
import com.yoyo.common.kafka.dto.PayInfoDTO;
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
    private final String UPDATE_TRANSACTION_MEMBER_PAY_TOPIC = "pay-update-transaction-member-topic";
    private final MemberService memberService;
    private final MemberProducer memberProducer;

    @KafkaListener(topics = "event-member-name-topic")
    public void getMemberName(EventMemberRequestDTO message) {
        Member member = memberService.findMemberById(message.getMemberId());
        memberProducer.sendMemberName(EventMemberResponseDTO.builder()
                                                      .memberId(member.getMemberId())
                                                      .name(member.getName())
                                                      .build());
    }

    /**
     * * 페이 송금 시 거래 내역 저장을 위해 발신자 이름 요청
     * @param : reqeust 페이 송금 거래내역 저장 정보
     * */
    @KafkaListener(topics = UPDATE_TRANSACTION_MEMBER_PAY_TOPIC, groupId = GROUP_ID)
    public void getMemberNameForPay(PayInfoDTO.RequestToTransaction request) {
        Member sender = memberService.findMemberById(request.getSenderId());
        request.setSenderName(sender.getName()); // 발신자 이름 저장
        // 거래내역 저장 요청
        memberProducer.sendPayInfoToTransaction(request);
    }
}
