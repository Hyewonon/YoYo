package com.yoyo.member.application.port.in.auth;

import com.yoyo.common.annotation.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginMemberCommand extends SelfValidating<LoginMemberCommand> {

    private final String phoneNumber;
    private final String password;
}
