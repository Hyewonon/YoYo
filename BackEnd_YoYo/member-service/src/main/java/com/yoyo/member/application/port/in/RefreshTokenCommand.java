package com.yoyo.member.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenCommand {

    private final String refreshToken;
}
