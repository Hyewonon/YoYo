package com.yoyo.member.domain.relation.repository;

import com.yoyo.member.entity.Relation;
import com.yoyo.member.entity.RelationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {

    List<Relation> findByMember_MemberId(Long memberId);

    // memberId와 isMember로 필터링
    List<Relation> findByMember_MemberIdAndIsMemberTrue(Long memberId);

    Optional<Relation> findByMember_MemberIdAndOppositeId(Long memberId, Long oppositeId);

    /**
     * 친구관계 존재 여부 확인
     * */
    @Query("SELECT count(r) > 0 FROM Relation r WHERE r.member.memberId = :memberId1 AND r.oppositeId = :memberId2")
    Boolean existedByMemberIds(Long memberId1, Long memberId2);

    /**
     * 두 멤버아이디로 친구관계 조회
     * */
    @Query("SELECT r FROM Relation r WHERE r.member.memberId = :memberId1 AND r.oppositeId = :memberId2")
    Optional<Relation> findByMemberAndOppositeId(Long memberId1, Long memberId2);

    List<Relation> findAllByMember_MemberIdAndOppositeName(Long memberId, String name);
}
