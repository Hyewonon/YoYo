package com.yoyo.member.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


public class RegisterMemberDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Schema(description = "회원가입 요청 DTO")
    public static class Request {
        private String name;
        private String phoneNumber;
        private String password;
        private LocalDate birthDay;
        private boolean isValid;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Schema(description = "회원가입 응답 DTO")
    public static class Response {
        private Long memberId;
        private String name;
        private String phoneNumber;
        private String password;
        private LocalDate birthDay;
        private boolean isValid;
        private String refreshToken;
    }
}
