package com.yoyo.member.domain.relation.consumer;

import com.yoyo.common.kafka.dto.IncreaseAmountDTO;
import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.common.kafka.dto.PayInfoDTO;
import com.yoyo.common.kafka.dto.TransactionDTO;
import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.relation.producer.RelationProducer;
import com.yoyo.member.domain.relation.repository.RelationRepository;
import com.yoyo.member.domain.relation.service.RelationService;
import com.yoyo.member.entity.Relation;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RelationConsumer {

    private final RelationService relationService;
    private final RelationRepository relationRepository;
    private final MemberRepository memberRepository;
    private final String UPDATE_RELATION_TOPIC = "pay-update-relation-topic";

    private final RelationProducer producer;

    /**
     * * 페이 송금 시 친구 관계 정보 수정
     *
     * @param : request 페이 송금 정보
     */
    @KafkaListener(topics = UPDATE_RELATION_TOPIC, concurrency = "3")
    public void updateRelation(PayInfoDTO.RequestToMember request) {
        // 1. realtion servie에서 친구 관계 있는지 확인
        if (!relationService.isAlreadyFriend(request.getSenderId(), request.getReceiverId())) {
            // 1.1. 없으면 생성
            relationService.createPayRelation(request.getSenderId(), request.getReceiverId());
        }
        // 2 친구 관계 보낸 총금액, 받은 총금액 수정
        relationService.updateRelationAmount(request.getSenderId(), request.getReceiverId(), request.getAmount());
    }

    @KafkaListener(topics = "transaction-register-topic", concurrency = "3")
    public void setRelation(IncreaseAmountDTO amount) {
        Optional<Relation> entity = relationRepository.findByMember_MemberIdAndOppositeId(amount.getMemberId(),
                                                                                          amount.getOppositeId());
        if (entity.isPresent()) {
            if (amount.isSend()) {
                entity.get().setTotalSentAmount(entity.get().getTotalSentAmount() + amount.getAmount());
            } else {
                entity.get().setTotalReceivedAmount(entity.get().getTotalReceivedAmount() + amount.getAmount());
            }
        } else {
            if (amount.isSend()) {
                Relation jpa = Relation.builder()
                                       .oppositeId(amount.getOppositeId())
                                       .totalSentAmount(amount.getAmount())
                                       .member(memberRepository.findByMemberId(amount.getMemberId()))
                                       .build();
                relationRepository.save(jpa);
            } else {
                Relation jpa = Relation.builder()
                                       .oppositeId(amount.getOppositeId())
                                       .totalReceivedAmount(amount.getAmount())
                                       .member(memberRepository.findByMemberId(amount.getMemberId()))
                                       .build();
                relationRepository.save(jpa);
            }
        }
    }

    @KafkaListener(topics = "payment-register", concurrency = "3")
    public void setPaymentRelation(TransactionDTO transactionDTO) {

    }

    @KafkaListener(topics = "notification-relation-topic", concurrency = "3")
    public void getMemberTagForMemberEvent(MemberTagDTO request) {
        producer.sendMemberTag(relationService.findRelationTag(request.getMemberId(), request.getOppositeId()));
    }
}
