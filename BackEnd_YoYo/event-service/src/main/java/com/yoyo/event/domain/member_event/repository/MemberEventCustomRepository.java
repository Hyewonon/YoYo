package com.yoyo.event.domain.member_event.repository;

import com.yoyo.event.domain.member_event.dto.MemberEventDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface MemberEventCustomRepository {

    List<MemberEventDTO.Response> customFindAllByMemberId(Long memberId, LocalDateTime now);
}
