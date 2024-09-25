package com.yoyo.member.application.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.MemberException;
import com.yoyo.member.adapter.dto.PayInfoDTO;
import com.yoyo.member.adapter.out.RelationResponse;
import com.yoyo.member.adapter.out.persistence.SpringDataMemberRepository;
import com.yoyo.member.adapter.out.persistence.SpringDataRelationRepository;
import com.yoyo.member.adapter.out.persistence.entity.MemberJpaEntity;
import com.yoyo.member.adapter.out.persistence.entity.RelationJpaEntity;
import com.yoyo.member.adapter.out.persistence.entity.RelationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelationService {
    private final SpringDataMemberRepository memberRepository;
    private final SpringDataRelationRepository relationRepository;

    public List<RelationResponse> findRelations(Long memberId, String  tag, String search, boolean isRegister) {
        RelationType relationType = null;
        if (tag != null) {
            try {
                relationType = RelationType.valueOf(tag.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        List<Long> oppositeIds = memberRepository.findByNameContaining(search);
        List<RelationJpaEntity> list = relationRepository.findByMember_MemberIdAndRelationTypeAndOppositeIdInAndIsRegister(memberId, relationType, oppositeIds, isRegister);
        return list.stream()
                .map(relation -> {
                    String oppositeName = memberRepository.findById(relation.getOppositeId())
                            .map(MemberJpaEntity::getName)
                            .orElse("Unknown");
                    return new RelationResponse(
                            relation.getRelationId(),
                            relation.getOppositeId(),
                            oppositeName,
                            relation.getRelationType(),
                            relation.getDescription(),
                            relation.getTotalReceivedAmount(),
                            relation.getTotalSentAmount()
                    );
                }).collect(Collectors.toList());
    }

    /**
     * 페이 송금 친구 관계 저장
     * */
    public void createPayRelation(Long memberId1, Long memberId2) {
        MemberJpaEntity member = findMemberByMemberId(memberId1);
        relationRepository.save(PayInfoDTO.Request.toNewEntity(member, memberId2));

        member = findMemberByMemberId(memberId2);
        relationRepository.save(PayInfoDTO.Request.toNewEntity(member, memberId1));
    }

    /**
     * 친구 관계 받은/ 보낸 총금액 수정
     * */
    public void updateRelationAmount(Long senderId, Long receiverId, Long amount){
        // 발신자 보낸 총 금액 수정
        RelationJpaEntity relationSender = relationRepository.findByMemberAndOppositeId(senderId, receiverId);
        relationSender.setTotalSentAmount(relationSender.getTotalSentAmount() + amount);

        // 수신자 받은 총 금액 수정
        RelationJpaEntity relationReceiver = relationRepository.findByMemberAndOppositeId(receiverId, senderId);
        relationReceiver.setTotalReceivedAmount(relationSender.getTotalReceivedAmount() + amount);
    }

    /**
    * * 이미 친구관계인지 확인
    * @param memberId1, memberId2
    * */
    public Boolean isAlreadyFriend(Long memberId1, Long memberId2) {
        return relationRepository.existedByMemberIds(memberId1, memberId2);
    }

    // repository 접근 메서드
    private MemberJpaEntity findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));
    }
}
