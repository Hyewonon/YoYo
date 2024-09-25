package com.yoyo.member.domain.relation.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.MemberException;
import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.relation.dto.RelationDTO;
import com.yoyo.member.domain.relation.repository.RelationRepository;
import com.yoyo.member.entity.Member;
import com.yoyo.member.entity.Relation;
import com.yoyo.member.entity.RelationType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RelationService {
    private final RelationRepository relationRepository;
    private final MemberRepository memberRepository;
    /**
     * 페이 송금 친구 관계 저장
     */
    public void createPayRelation(Long memberId1, Long memberId2) {
        Member member = findMemberByMemberId(memberId1);
        relationRepository.save(toNewEntity(member, memberId2));

        member = findMemberByMemberId(memberId2);
        relationRepository.save(toNewEntity(member, memberId1));
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
    public void updateRelationAmount(Long senderId, Long receiverId, Long amount) {
        // 발신자 보낸 총 금액 수정
        Relation relationSender = relationRepository.findByMemberAndOppositeId(senderId, receiverId)
                .orElseThrow(() -> new MemberException(
                        ErrorCode.NOT_FOUND_RELATION));
        relationSender.setTotalSentAmount(relationSender.getTotalSentAmount() + amount);
        relationRepository.save(relationSender);

        // 수신자 받은 총 금액 수정
        Relation relationReceiver = relationRepository.findByMemberAndOppositeId(receiverId, senderId)
                .orElseThrow(() -> new MemberException(
                        ErrorCode.NOT_FOUND_RELATION));
        ;
        relationReceiver.setTotalReceivedAmount(relationReceiver.getTotalReceivedAmount() + amount);
        relationRepository.save(relationReceiver);
    }

    // repository 접근 메서드
    private Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));
    }

    private Relation toNewEntity(Member member, Long oppositeId) {
        return Relation.builder()
                .member(member)
                .oppositeId(oppositeId)
                .relationType(RelationType.NONE)
                .description("")
                .totalReceivedAmount(0L)
                .totalSentAmount(0L)
                .build();
    }
    public List<RelationDTO.Response> findRelations(Long memberId, String tag, String search, boolean isRegister) {
        RelationType relationType = null;
        if (tag != null) {
            try {
                relationType = RelationType.valueOf(tag.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        List<Long> oppositeIds = memberRepository.findByNameContaining(search);
        List<Relation> list = relationRepository.findByMember_MemberIdAndRelationTypeAndOppositeIdInAndIsRegister(
                memberId, relationType, oppositeIds, isRegister);
        return list.stream()
                .map(relation -> {
                    String oppositeName = memberRepository.findById(relation.getOppositeId())
                            .map(Member::getName)
                            .orElse("Unknown");
                    return RelationDTO.Response.builder()
                            .relationId(relation.getRelationId())
                            .oppositeId(relation.getOppositeId())
                            .oppositeName(oppositeName)
                            .relationType(String.valueOf(relation.getRelationType()))
                            .description(relation.getDescription())
                            .totalReceivedAmount(relation.getTotalReceivedAmount())
                            .totalSentAmount(relation.getTotalSentAmount())
                            .build();
                }).collect(Collectors.toList());
    }
}
