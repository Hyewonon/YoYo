package com.yoyo.member.domain.member.repository;

import com.yoyo.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByPhoneNumber(String phoneNumber);
    Member findByMemberId(Long memberId);

    boolean existsByPhoneNumber(String phoneNumber);
    // 이름으로 관계의 상대방의 memberId 리스트조회
    List<Long> findByNameContaining(String name);
}
