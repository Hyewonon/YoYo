package com.yoyo.member.application.port.out;

import com.yoyo.member.domain.Member;

public interface UpdateMemberPort {

    Member findMemberById(Long memberId);
    void save(Member member);
}
