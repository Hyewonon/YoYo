package com.yoyo.member.application.port.out;

import com.yoyo.member.adapter.out.persistence.MemberJpaEntity;
import com.yoyo.member.domain.Member;

public interface FindMemberPort {

    MemberJpaEntity findMemberById(Member.MemberId memberId);
}
