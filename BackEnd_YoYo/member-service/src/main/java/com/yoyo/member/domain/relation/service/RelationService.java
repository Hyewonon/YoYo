package com.yoyo.member.domain.relation.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.MemberException;
import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.relation.dto.RelationDTO;
import com.yoyo.member.domain.relation.dto.UpdateRelationDTO;
import com.yoyo.member.domain.relation.repository.RelationRepository;
import com.yoyo.member.entity.Member;
import com.yoyo.member.entity.Relation;
import com.yoyo.member.entity.RelationType;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        relationRepository.save(toNewEntityForPay(member, memberId2));

        member = findMemberByMemberId(memberId2);
        relationRepository.save(toNewEntityForPay(member, memberId1));
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

    /**
     * TODO 친구관계 정보 수정
     */
    public void updateRelation(Long memberId, UpdateRelationDTO.Request request) {
        Relation relation = relationRepository.findByMember_MemberIdAndOppositeId(memberId, request.getMemberId())
                                              .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_RELATION));
        relation.setRelationType(RelationType.valueOf(request.getRelationType()));
        relation.setDescription(request.getDescription());
        relationRepository.save(relation);
    }

    public List<RelationDTO.Response> findRelations(Long memberId, String tag, String search) {
        RelationType relationType = (tag != null) ? RelationType.valueOf(tag.toUpperCase()) : null;
        List<Relation> relations;
        if (relationType != null && search != null) {
            relations = relationRepository.findByMember_MemberIdAndRelationTypeAndOppositeNameContaining(
                    memberId, relationType, search
            );
        } else if (relationType != null) {
            relations = relationRepository.findByMember_MemberIdAndRelationType(
                    memberId, relationType
            );
        } else if (search != null) {
            relations = relationRepository.findByMember_MemberIdAndOppositeNameContaining(
                    memberId, search
            );
        } else {
            relations = relationRepository.findByMember_MemberId(memberId);
        }
        return relations.stream().map(
                relation -> RelationDTO.Response.builder().build()
        ).collect(Collectors.toList());
    }

    // repository 접근 메서드
    private Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                               .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));
    }

    private Relation toNewEntityForPay(Member member, Long oppositeId) {
        return Relation.builder()
                       .member(member)
                       .oppositeId(oppositeId)
                       .relationType(RelationType.NONE)
                       .description("")
                       .totalReceivedAmount(0L)
                       .totalSentAmount(0L)
                       .isMember(true)
                       .build();
    }

    public MemberTagDTO findRelationTag(Long memberId, Long oppositeId) {
        Relation relation = relationRepository.findByMemberAndOppositeId(memberId, oppositeId)
                                             .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));
        return new MemberTagDTO(relation.getMember().getMemberId(), relation.getOppositeId(), relation.getRelationType().toString());
    }
}
