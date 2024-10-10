package com.yoyo.banking.domain.account.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountPinDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[ AccountPinDTO ] 계좌 PIN 번호 수정/확인 요청 DTO")
    public static class Request{
        @NotBlank(message = "결제 비밀번호를 입력하세요.")
        @Pattern(regexp = "^[0-9]{6}$", message = "6자리 숫자만 입력할 수 있습니다.")
        @Schema(description = "결제 비밀번호 : 6자리 숫자만 입력가능")
        private String pin;
    }
}
