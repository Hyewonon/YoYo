package com.yoyo.member.application.port.in.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenCommand {

    private final String refreshToken;
}
