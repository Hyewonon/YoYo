package com.yoyo.eureka.domain.member_event.repository;

import com.yoyo.eureka.entity.MemberEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberEventRepository extends JpaRepository<MemberEvent, Long>, MemberEventCustomRepository {

    boolean existsByMemberIdAndEventId(Long memberId, Long eventId);
}