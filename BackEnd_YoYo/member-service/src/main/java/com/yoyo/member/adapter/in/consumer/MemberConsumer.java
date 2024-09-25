package com.yoyo.member.adapter.in.consumer;

import com.yoyo.common.kafka.dto.PayInfoDTO;
import com.yoyo.member.adapter.out.producer.MemberProducer;
import com.yoyo.member.application.port.in.member.FindMemberCommand;
import com.yoyo.member.application.service.FindMemberService;
import com.yoyo.common.kafka.dto.EventMemberResponseDTO;
import com.yoyo.common.kafka.dto.EventMemberRequestDTO;
import com.yoyo.member.application.service.RelationService;
import com.yoyo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberConsumer {

    private final String GROUP_ID = "member-service";
    private final String UPDATE_RELATION_TOPIC = "pay-update-relation-topic";
    private final String UPDATE_TRANSACTION_MEMBER_TOPIC = "pay-update-transaction-member-topic";
    private final RelationService relationService;
    private final FindMemberService findMemberService;
    private final MemberProducer producer;

    private final MemberProducer memberProducer;

    /**
     * * 페이 송금 시 친구 관계 정보 수정
     *
     * @param : request 페이 송금 정보
     */
    @KafkaListener(topics = UPDATE_RELATION_TOPIC, groupId = GROUP_ID)
    public void updateRelation(PayInfoDTO.RequestToMember request) {
        // 1. realtion servie에서 친구 관계 있는지 확인
        if (!relationService.isAlreadyFriend(request.getSenderId(), request.getReceiverId())) {
            // 1.1. 없으면 생성
            relationService.createPayRelation(request.getSenderId(), request.getReceiverId());
        }
        // 2 친구 관계 보낸 총금액, 받은 총금액 수정
        relationService.updateRelationAmount(request.getSenderId(), request.getReceiverId(), request.getAmount());
    }

    @KafkaListener(topics = "event-member-name-topic")
    public void getMemberName(EventMemberRequestDTO message) {
        log.info("RECEIVE MEMBER NAME FOR EVENT");
        Member member = findMemberService.findMember(
                FindMemberCommand.builder().memberId(message.getMemberId()).build());
        producer.sendMemberName(EventMemberResponseDTO.builder()
                                                      .memberId(member.getMemberId())
                                                      .name(member.getName())
                                                      .build());
    }

    /**
     * * 페이 송금 시 거래 내역 저장을 위해 발신자 이름 요청
     * @param : reqeust 페이 송금 거래내역 저장 정보
     * */
    @KafkaListener(topics = UPDATE_TRANSACTION_MEMBER_TOPIC, groupId = GROUP_ID)
    public void getMemberNameForPay(PayInfoDTO.RequestToTransaction request) {
        FindMemberCommand command = FindMemberCommand.builder()
                                                     .memberId(request.getSenderId())
                                                     .build();
        Member sender = findMemberService.findMember(command);
        request.setSenderName(sender.getName()); // 발신자 이름 저장

        // 거래내역 저장 요청
        memberProducer.sendPayInfoToTransaction(request);
    }
}
