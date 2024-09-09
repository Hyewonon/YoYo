package com.yoyo.banking.domain.account.dto;

import com.yoyo.banking.entity.Account;
import com.yoyo.common.util.PasswordEncryptUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class AccountCreateDTO {

    /*
     * * 회원 가입, 회원 정보 수정 요청 DTO
     * */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[ AccountCreateDTO ] 계좌 등록(수정) 요청 DTO")
    public static class Request {

        @NotBlank(message = "계좌번호를 입력해주세요.")
        private String accountNumber;
        @NotBlank(message = "은행이름을 입력해주세요.")
        private String bankName;
        @NotNull(message = "계좌를 인증하세요.")
        @AssertTrue(message = "인증되지 않은 계좌입니다.")
        @Schema(description = "1원 송금 인증 여부 : true만 허용")
        private Boolean isAuthenticated;
        @NotNull(message = "결제 비밀번호를 입력하세요.")
        private String pin;

        public static Account toEntity(AccountCreateDTO.Request request, Long memberId, Long bankCode) {
            return Account.builder()
                          .memberId(memberId)
                          .accountNumber(PasswordEncryptUtil.encrypt(request.getAccountNumber()))
                          .bankCode(bankCode)
                          .balance(0L)
                          .pin(PasswordEncryptUtil.encrypt(request.getPin()))
                          .build();
        }
    }
}
