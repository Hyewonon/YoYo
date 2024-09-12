package com.yoyo.banking.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class DummyAccountDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[ DummyAccountDTO ] 싸피은행 더미 계좌 응답 DTO")
    public static class Response {
        private Long memberId;
        private String bankName;
        private String accountNo;

        public static Response of(String account, String bankName, Long memberId) {
            return Response.builder()
                    .memberId(memberId)
                    .bankName(bankName)
                    .accountNo(account)
                    .build();
        }
    }
}
