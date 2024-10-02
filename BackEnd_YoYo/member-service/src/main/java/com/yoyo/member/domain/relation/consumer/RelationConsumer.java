package com.yoyo.member.domain.relation.consumer;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.kafka.dto.*;
import com.yoyo.common.kafka.dto.PayInfoDTO.RequestToMember;
import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.member.service.MemberService;
import com.yoyo.member.domain.relation.producer.RelationProducer;
import com.yoyo.member.domain.relation.repository.RelationRepository;
import com.yoyo.member.domain.relation.service.RelationService;
import com.yoyo.member.entity.NoMember;
import com.yoyo.member.entity.Relation;
import com.yoyo.member.entity.RelationType;

import java.util.List;
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
    private final MemberService memberService;

    private final RelationRepository relationRepository;
    private final MemberRepository memberRepository;

    private final String UPDATE_RELATION_TOPIC = "pay-update-relation-topic";
    private final String CREATE_TRANSACTION_SELF_RELATION_TOPIC = "create-transaction-self-relation-topic";
    private final String GET_RELATION_IDS = "get-relations-ids";
    private final RelationProducer relationProducer;

    /**
     * * 페이 송금 시 친구 관계 정보 수정
     *
     * @param : request 페이 송금 정보
     */
    @KafkaListener(topics = UPDATE_RELATION_TOPIC, concurrency = "3")
    public void updateRelation(RequestToMember request) {
        // 1. realtion servie에서 친구 관계 있는지 확인
        if (!relationService.isAlreadyFriend(request.getSenderId(), request.getReceiverId())) {
            // 1.1. 없으면 생성
            relationService.createRelation(request.getSenderId(), request.getReceiverId(), RelationType.NONE, true);
        }
        // 2 친구 관계 보낸 총금액, 받은 총금액 수정
        relationService.updateRelationAmount(request.getSenderId(), request.getReceiverId(), request.getAmount(), true);
        relationService.updateRelationAmount(request.getReceiverId(), request.getSenderId(), request.getAmount(),
                false);
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
            Relation jpa;
            if (amount.isSend()) {
                jpa = Relation.builder()
                        .oppositeId(amount.getOppositeId())
                        .totalSentAmount(amount.getAmount())
                        .member(memberRepository.findByMemberId(amount.getMemberId()))
                        .build();
            } else {
                jpa = Relation.builder()
                        .oppositeId(amount.getOppositeId())
                        .totalReceivedAmount(amount.getAmount())
                        .member(memberRepository.findByMemberId(amount.getMemberId()))
                        .build();
            }
            relationRepository.save(jpa);
        }
    }

    @KafkaListener(topics = "notification-relation-topic", concurrency = "3")
    public void getMemberTagForMemberEvent(MemberTagDTO request) {
        relationProducer.sendMemberTag(relationService.findRelationTag(request.getMemberId(), request.getOppositeId()));
    }

    /**
     * 요요 거래내역 직접등록 시 친구 관계
     */
    @KafkaListener(topics = CREATE_TRANSACTION_SELF_RELATION_TOPIC, concurrency = "3")
    public void createTransactionSelf(TransactionSelfRelationDTO.RequestToMember request) {
        // 0. memberId 0이면 비회원 등록
        if (request.getOppositeId() == 0) {
            NoMember noMember = memberService.saveNoMember(request.getOppositeName());
            request.setOppositeId(noMember.getMemberId());
        }

        // 1. realtion servie에서 친구 관계 있는지 확인
        if (!relationService.isAlreadyFriend(request.getMemberId(), request.getOppositeId())) {
            // 1.1. 없으면 생성
            relationService.createRelation(request.getMemberId(), request.getOppositeId(),
                                           RelationType.valueOf(request.getRelationType()), false);
        }
        // 2 친구 관계 보낸 총금액, 받은 총금액 수정
        // 보낸사람이 등록하면 보낸사람 친구관계만 수정
        relationService.updateRelationAmount(request.getMemberId(), request.getOppositeId(), request.getAmount(),
                                             request.getTransactionType().equals("SEND"));

        relationProducer.sendTransactionSelf(createResponse(request));
    }

    @KafkaListener(topics = GET_RELATION_IDS, concurrency = "3")
    public void getRelationIDList(MemberRequestDTO request) {
        relationProducer.sendRelationIds(relationService.findRelationIds(request.getMemberId()));
    }

    private TransactionSelfRelationDTO.ResponseFromMember createResponse(
            TransactionSelfRelationDTO.RequestToMember request) {
        String memberName = memberService.findBaseMemberNameById(request.getMemberId());
        String oppositeName = memberService.findBaseMemberNameById(request.getOppositeId());

        return TransactionSelfRelationDTO.ResponseFromMember.builder()
                                                            .memberId(request.getMemberId())
                                                            .memberName(memberName)
                                                            .oppositeId(request.getOppositeId())
                                                            .oppositeName(oppositeName)
                                                            .build();
    }

    @KafkaListener(topics = "relation-description-topic", concurrency = "3")
    public void getDescription(FindDescriptionDTO.Request request) {
        Relation relation = relationRepository.findByMember_MemberIdAndOppositeId(request.getMemberId(), request.getOppositeId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RELATION));
        FindDescriptionDTO.Response response = FindDescriptionDTO.Response.builder().description(relation.getDescription()).build();
        relationProducer.sendDescriptionResponse(response);
    }

    @KafkaListener(topics = "match-relation", concurrency = "3")
    public void getMatchRelation(TransactionDTO.MatchRelation request) {
        List<Relation> list = relationRepository.findAllByMember_MemberIdAndOppositeName(request.getMemberId(), request.getName());
        if (list.isEmpty()) {
            request.setRelationStatus(0);
        } else if (list.size() == 1) {
            request.setRelationStatus(1);
        } else {
            request.setRelationStatus(2);
        }
        relationProducer.sendResultMatch(request);
    }

    @KafkaListener(topics = "update-relation-topic", concurrency = "3")
    public void getUpdateRelation(UpdateRelationDTO.Request request) {
        Relation relation;
        // 있는 관계 덮어쓰기
        if (request.getRelationId() != null) {
            relation = relationRepository.findByRelationId(request.getRelationId());
            relation.setTotalReceivedAmount(relation.getTotalReceivedAmount() + request.getAmount());
        }
        // 새로 저장
        else {
            relation = Relation.builder()
                    .member(memberRepository.findByMemberId(request.getMemberId()))
                    .relationType(RelationType.valueOf(request.getRelationType()))
                    .oppositeId(request.getOppositeId())
                    .oppositeName(request.getName())
                    .description(request.getDescription())
                    .totalReceivedAmount(request.getAmount())
                    .build();
        }
        relationRepository.save(relation);
    }
}
