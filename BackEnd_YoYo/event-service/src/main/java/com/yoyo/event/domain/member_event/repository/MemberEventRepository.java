package com.yoyo.event.domain.member_event.repository;

import com.yoyo.event.entity.MemberEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberEventRepository extends JpaRepository<MemberEvent, Long>, MemberEventCustomRepository {

    boolean existsByMemberIdAndEventId(Long memberId, Long eventId);
}