package com.yoyo.member.application.service;

import com.yoyo.member.adapter.out.RelationResponse;
import com.yoyo.member.adapter.out.persistence.SpringDataMemberRepository;
import com.yoyo.member.adapter.out.persistence.SpringDataRelationRepository;
import com.yoyo.member.adapter.out.persistence.entity.MemberJpaEntity;
import com.yoyo.member.adapter.out.persistence.entity.RelationJpaEntity;
import com.yoyo.member.adapter.out.persistence.entity.RelationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelationService {
    private final SpringDataMemberRepository memberRepository;
    private final SpringDataRelationRepository relationRepository;

    public List<RelationResponse> findRelations(Long memberId, String  tag, String search) {
        RelationType relationType = null;
        if (tag != null) {
            try {
                relationType = RelationType.valueOf(tag.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        List<Long> oppositeIds = memberRepository.findByNameContaining(search);
        List<RelationJpaEntity> list = relationRepository.findByMember_MemberIdAndRelationTypeAndOppositeIdIn(memberId, relationType, oppositeIds);
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
}
