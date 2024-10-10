package com.yoyo.member.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LoginMemberDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "로그인 요청 DTO")
    public static class Request {
        private String phoneNumber;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "로그인 응답 DTO")
    public static class Response {
        private Long memberId;
        private String jwtToken;
        private String refreshToken;
    }
}
