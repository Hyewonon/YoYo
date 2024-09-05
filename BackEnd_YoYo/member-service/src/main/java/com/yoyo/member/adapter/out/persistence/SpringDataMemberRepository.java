package com.yoyo.member.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMemberRepository extends JpaRepository<MemberJpaEntity, Long> {

}
