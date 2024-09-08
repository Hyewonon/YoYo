package com.yoyo.member.application.port.in;

import com.yoyo.common.annotation.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginMemberCommand extends SelfValidating<LoginMemberCommand> {

    private final Long memberId;
}
