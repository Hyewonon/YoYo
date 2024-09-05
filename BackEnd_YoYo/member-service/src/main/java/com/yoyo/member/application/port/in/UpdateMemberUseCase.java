package com.yoyo.member.application.port.in;

import com.yoyo.member.domain.Member;

public interface UpdateMemberUseCase {

    void updateMember(UpdateMemberCommand command);

}
