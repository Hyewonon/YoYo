package com.yoyo.member.domain.relation.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.MemberException;
import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.member.service.MemberService;
import com.yoyo.member.domain.relation.dto.RelationDTO;
import com.yoyo.member.domain.relation.dto.UpdateRelationDTO;
import com.yoyo.member.domain.relation.repository.RelationRepository;
import com.yoyo.member.entity.Member;
import com.yoyo.member.entity.NoMember;
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
    private final MemberService memberService;


    /**
     * 페이 송금 친구 관계 저장
     * @param memberId1 회원
     * @param memberId2 회원 혹은 비회원
     * @param isMember memberId2가 회원인지 비회원인지
     */
    public void createRelation(Long memberId1, Long memberId2, RelationType relationType, Boolean isMember){
        Member member = memberService.findMemberById(memberId1);
        relationRepository.save(toNewEntityForPay(member, memberId2, relationType, isMember));

        if(isMember){
            // 둘다 회원이라면 양방향으로 저장
            member = memberService.findMemberById(memberId2);
            relationRepository.save(toNewEntityForPay(member, memberId1, relationType, true));
        }
    }

    /**
     * 비회원 결제 친구 관계 저장
     */
    public String createPaymentRelation(NoMember noMember, Long memberId, Long amount) {
        Member member = memberService.findMemberById(memberId);
        relationRepository.save(toNewEntityForPayment(member, noMember.getMemberId(), amount));
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
        if(isSender){
            updatedAmount = relation.getTotalSentAmount() + amount;
        } else updatedAmount = relation.getTotalReceivedAmount() + amount;
        relation.setTotalSentAmount(updatedAmount);
        relationRepository.save(relation);
    }

    /**
     * 친구관계 정보 수정
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

    /*
     * Get Tag For Notification
     */
    public MemberTagDTO findRelationTag(Long memberId, Long oppositeId) {
        Relation relation = relationRepository.findByMemberAndOppositeId(memberId, oppositeId)
                                              .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));
        return new MemberTagDTO(relation.getMember().getMemberId(), relation.getOppositeId(), relation.getRelationType().toString(), relation.getDescription());
    }

    private Relation toNewEntityForPay(Member member, Long oppositeId, RelationType relationType, Boolean isMember) {
        return Relation.builder()
                       .member(member)
                       .oppositeId(oppositeId)
                       .relationType(relationType)
                       .description("")
                       .totalReceivedAmount(0L)
                       .totalSentAmount(0L)
                       .isMember(isMember)
                       .build();
    }

    private Relation toNewEntityForPayment(Member member, Long noMemberId, Long amount) {
        return Relation.builder()
                       .member(member)
                       .oppositeId(noMemberId)
                       .relationType(RelationType.NONE)
                       .description("")
                       .totalReceivedAmount(amount)
                       .totalSentAmount(0L)
                       .isMember(false)
                       .build();
    }
}
