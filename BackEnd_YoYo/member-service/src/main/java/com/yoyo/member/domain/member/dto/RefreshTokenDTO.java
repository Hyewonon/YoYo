package com.yoyo.member.domain.member.dto;

import lombok.AllArgsConstructor;

public class RefreshTokenDTO {
    @AllArgsConstructor
    public static class Response{
        private Long memberId;
        private String JwtToken;
        private String refreshToken;
    }
}
