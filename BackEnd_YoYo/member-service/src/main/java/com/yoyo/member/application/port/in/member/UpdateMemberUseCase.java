package com.yoyo.member.application.port.in.member;

import com.yoyo.member.domain.Member;

public interface UpdateMemberUseCase {

    Member updateMember(UpdateMemberCommand command);

}
