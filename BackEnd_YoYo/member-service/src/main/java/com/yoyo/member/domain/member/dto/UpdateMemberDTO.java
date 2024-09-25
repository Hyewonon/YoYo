package com.yoyo.member.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class UpdateMemberDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "정보변경 요청 DTO")
    public static class Request {
        private String name;
        private String phoneNumber;
        private LocalDate birthDay;
    }
}
