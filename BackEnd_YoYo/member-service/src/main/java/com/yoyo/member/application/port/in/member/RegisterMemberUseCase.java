package com.yoyo.member.application.port.in.member;

import com.yoyo.member.domain.Member;

public interface RegisterMemberUseCase {

    Member registerMember(RegisterMemberCommand command);
}
