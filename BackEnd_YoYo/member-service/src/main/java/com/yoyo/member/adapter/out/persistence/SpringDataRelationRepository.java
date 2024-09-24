package com.yoyo.member.adapter.out.persistence;

import com.yoyo.member.adapter.out.persistence.entity.RelationJpaEntity;
import com.yoyo.member.adapter.out.persistence.entity.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataRelationRepository extends JpaRepository<RelationJpaEntity, Long> {
    List<RelationJpaEntity> findByMember_MemberIdAndRelationTypeAndOppositeIdInAndIsRegister(
            Long memberId, RelationType relationType, List<Long> oppositeIds, boolean isRegister);
    Optional<RelationJpaEntity> findByMember_MemberIdAndOppositeId(Long memberId, Long oppositeId);
}
