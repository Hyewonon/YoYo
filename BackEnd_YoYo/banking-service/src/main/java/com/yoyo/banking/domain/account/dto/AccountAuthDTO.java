package com.yoyo.banking.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountAuthDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[ AccountAuthDTO ] 1원 송금 관련 요청 DTO")
    public static class Request {
        @NotBlank(message="계좌번호를 입력하세요.")
        private String accountNumber;
        @Nullable
        private String authCode;
    }
}
