package com.yoyo.member.domain.relation.service;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.MemberException;
import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.common.kafka.dto.RelationResponseDTO;
import com.yoyo.common.kafka.dto.UpdateTransactionRelationTypeDTO;
import com.yoyo.member.domain.member.service.MemberService;
import com.yoyo.member.domain.relation.dto.FindRelationDTO;
import com.yoyo.member.domain.relation.dto.UpdateRelationDTO;
import com.yoyo.member.domain.relation.producer.RelationProducer;
import com.yoyo.member.domain.relation.repository.RelationRepository;
import com.yoyo.member.entity.Member;
import com.yoyo.member.entity.NoMember;
import com.yoyo.member.entity.Relation;
import com.yoyo.member.entity.RelationType;
import jakarta.transaction.Transactional;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RelationService {

    private final RelationRepository relationRepository;
    private final MemberService memberService;
    private final RelationProducer relationProducer;

    /**
     * 페이 송금 / 직접 등록 친구 관계 저장
     *
     * @param memberId1 회원
     * @param memberId2 회원 혹은 비회원
     * @param isMember  memberId2가 회원인지 비회원인지
     */
    public void createRelation(Long memberId1, Long memberId2, RelationType relationType, Boolean isMember, String description) {
        Member member = memberService.findMemberById(memberId1);
        String member2Name = memberService.findBaseMemberNameById(memberId2);
        relationRepository.save(toNewEntityForPay(member, memberId2, member2Name, relationType, isMember, description));

        if (isMember) {
            // 둘다 회원이라면 양방향으로 저장
            member = memberService.findMemberById(memberId2);
            String member1Name = memberService.findBaseMemberNameById(memberId1);
            relationRepository.save(toNewEntityForPay(member, memberId1, member1Name, relationType, true, description));
        }
    }

    /**
     * 비회원 결제 친구 관계 저장
     */
    public String createPaymentRelation(NoMember noMember, Long memberId, String description) {
        Member member = memberService.findMemberById(memberId);
        relationRepository.save(toNewEntityForPayment(member, noMember.getMemberId(), description, noMember.getName()));
        return member.getName();
    }

    /**
     * * 이미 친구관계인지 확인
     *
     * @param memberId1, memberId2
     */
    public Boolean isAlreadyFriend(Long memberId1, Long memberId2) {
        return relationRepository.existedByMemberIds(memberId1, memberId2);
    }

    /**
     * 친구 관계 받은/ 보낸 총금액 수정
     */
    public void updateRelationAmount(Long memberId1, Long memberId2, Long amount, Boolean isSender) {
        Relation relation = relationRepository.findByMemberAndOppositeId(memberId1, memberId2)
                .orElseThrow(() -> new MemberException(
                        ErrorCode.NOT_FOUND_RELATION));
        Long updatedAmount;
        if (isSender) {
            updatedAmount = relation.getTotalSentAmount() + amount;
            relation.setTotalSentAmount(updatedAmount);
        } else {
            updatedAmount = relation.getTotalReceivedAmount() + amount;
            relation.setTotalReceivedAmount(updatedAmount);
        }
        relationRepository.save(relation);
    }

    /**
     * 친구관계 정보 수정
     */
    public Relation updateRelation(Long memberId, UpdateRelationDTO.Request request) {
        Relation relation = relationRepository.findByMember_MemberIdAndOppositeId(memberId, request.getMemberId())
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_RELATION));
        relation.setRelationType(RelationType.valueOf(request.getRelationType()));
        relation.setDescription(request.getDescription());
        Relation updatedRelation = relationRepository.save(relation);
        UpdateTransactionRelationTypeDTO updateTransactionRelationTypeDTO = UpdateTransactionRelationTypeDTO.builder()
                .memberId(memberId)
                .oppositeId(updatedRelation.getOppositeId())
                .relationType(updatedRelation.getRelationType().toString())
                .build();
        relationProducer.sendUpdateTransactionRelationType(updateTransactionRelationTypeDTO);
        return updatedRelation;
    }

    public List<FindRelationDTO.Response> findRelations(Long memberId, String tag, String search) {
        String validatedTag = (tag == null || tag.trim().isEmpty()) ? null : tag;
        String validatedSearch = (search == null || search.trim().isEmpty()) ? null : search;
        List<Relation> relations = relationRepository.findByMember_MemberId(memberId);
        return relations.stream()
                .filter(relation -> validatedTag == null || relation.getRelationType().toString().equals(validatedTag))
                .filter(relation -> validatedSearch == null ||
                        (relation.getOppositeName() != null && relation.getOppositeName().contains(validatedSearch)))
                .sorted(Comparator.comparing(Relation::getOppositeName, Collator.getInstance()))
                .map(relation -> FindRelationDTO.Response.builder()
                        .relationId(relation.getRelationId())
                        .oppositeId(relation.getOppositeId())
                        .oppositeName(relation.getOppositeName())
                        .relationType(relation.getRelationType().toString())
                        .description(relation.getDescription())
                        .totalReceivedAmount(relation.getTotalSentAmount())
                        .totalSentAmount(relation.getTotalReceivedAmount())
                        .build()).collect(Collectors.toList());
    }

    /*
     * Get Tag For Notification
     */
    public MemberTagDTO findRelationTag(Long memberId, Long oppositeId) {
        Relation relation = relationRepository.findByMemberAndOppositeId(memberId, oppositeId)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));
        return new MemberTagDTO(relation.getMember().getMemberId(), relation.getOppositeId(), relation.getRelationType().toString(), relation.getDescription());
    }

    public RelationResponseDTO findRelationIds(Long memberId) {
        List<Relation> relations = relationRepository.findByMember_MemberIdAndIsMemberTrue(memberId);
        List<Long> oppositeIds = relations.stream()
                .map(Relation::getOppositeId)
                .toList();
        return RelationResponseDTO.of(memberId, oppositeIds);
    }

    private Relation toNewEntityForPay(Member member, Long oppositeId, String oppositeName, RelationType relationType, Boolean isMember, String description) {
        return Relation.builder()
                .member(member)
                .oppositeId(oppositeId)
                .oppositeName(oppositeName)
                .relationType(relationType)
                .description(description)
                .totalReceivedAmount(0L)
                .totalSentAmount(0L)
                .isMember(isMember)
                .build();
    }

    private Relation toNewEntityForPayment(Member member, Long noMemberId, String description, String name) {
        RelationType relationType;
        if (description.contains("친구")) {
            relationType = RelationType.FRIEND;
        } else if (description.contains("직장") || description.contains("회사") || description.contains("상사")) {
            relationType = RelationType.COMPANY;
        } else if (description.contains("친척") || description.contains("가족") || description.contains("사촌")) {
            relationType = RelationType.FAMILY;
        } else {
            relationType = RelationType.ETC;
        }
        return Relation.builder()
                .member(member)
                .oppositeId(noMemberId)
                .oppositeName(name)
                .relationType(relationType)
                .description(description)
                .totalSentAmount(0L)
                .isMember(false)
                .build();
    }

    public Map<String, Object> findRelation(Long memberId, Long oppositeId) {
        Relation relation = relationRepository.findByMember_MemberIdAndOppositeId(memberId, oppositeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RELATION));
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("oppositeName", relation.getOppositeName());
        responseMap.put("description", relation.getDescription());
        responseMap.put("relationType", relation.getRelationType().toString());
        responseMap.put("totalReceivedAmount", relation.getTotalReceivedAmount());
        responseMap.put("totalSentAmount", relation.getTotalSentAmount());

        return responseMap;
    }
}
