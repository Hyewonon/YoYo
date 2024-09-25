package com.yoyo.member.adapter.out.persistence;

import com.yoyo.member.adapter.out.persistence.entity.RelationJpaEntity;
import com.yoyo.member.adapter.out.persistence.entity.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataRelationRepository extends JpaRepository<RelationJpaEntity, Long> {
    List<RelationJpaEntity> findByMember_MemberIdAndRelationTypeAndOppositeIdInAndIsRegister(
            Long memberId, RelationType relationType, List<Long> oppositeIds, boolean isRegister);
    Optional<RelationJpaEntity> findByMember_MemberIdAndOppositeId(Long memberId, Long oppositeId);
    List<RelationJpaEntity> findByMember_MemberIdAndRelationTypeAndOppositeIdIn(
            Long memberId, RelationType relationType, List<Long> oppositeIds);

    /**
     * 친구관계 존재 여부 확인
     * */
    @Query("SELECT count(r) > 0 FROM RelationJpaEntity r WHERE r.member.memberId = :memberId1 AND r.oppositeId = :memberId2")
    Boolean existedByMemberIds(Long memberId1, Long memberId2);

    /**
     * 두 멤버아이디로 친구관계 조회
     * */
    @Query("SELECT r FROM RelationJpaEntity r WHERE r.member.memberId = :memberId1 AND r.oppositeId = :memberId2")
    Optional<RelationJpaEntity> findByMemberAndOppositeId(Long memberId1, Long memberId2);
}
