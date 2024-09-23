package com.yoyo.member.adapter.out.persistence;

import com.yoyo.member.adapter.out.persistence.entity.RelationJpaEntity;
import com.yoyo.member.adapter.out.persistence.entity.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataRelationRepository extends JpaRepository<RelationJpaEntity, Long> {
    List<RelationJpaEntity> findByMember_MemberIdAndRelationTypeAndOppositeIdIn(
            Long memberId, RelationType relationType, List<Long> oppositeIds);
}
