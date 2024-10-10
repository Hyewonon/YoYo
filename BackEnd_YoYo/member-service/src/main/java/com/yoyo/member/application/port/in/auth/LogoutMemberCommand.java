package com.yoyo.member.application.port.in.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LogoutMemberCommand {

    private String jwtToken;
}
