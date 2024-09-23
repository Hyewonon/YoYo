package com.yoyo.member.adapter.out.persistence;

import com.yoyo.member.adapter.out.persistence.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataMemberRepository extends JpaRepository<MemberJpaEntity, Long> {

    MemberJpaEntity findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
    // 이름으로 관계의 상대방의 memberId 리스트조회
    List<Long> findByNameContaining(String name);
}
