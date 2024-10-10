package com.yoyo.member.domain.member.repository;

import com.yoyo.member.entity.BaseMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseMemberRepository extends JpaRepository<BaseMember, Long> {

}
