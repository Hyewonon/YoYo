package com.yoyo.banking.domain.account.dto.account;

import com.yoyo.banking.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

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
        @NotBlank(message = "결제 비밀번호를 입력하세요.")
        @Pattern(regexp = "^[0-9]{6}$", message = "6자리 숫자만 입력할 수 있습니다.")
        @Schema(description = "결제 비밀번호 : 6자리 숫자만 입력가능")
        private String pin;

        // TODO : 계좌번호 저장시 양방향 암호화 알고리즘 적용 필요
        public static Account toEntity(AccountCreateDTO.Request request, Long memberId, String bankCode) {
            String hashedPin = BCrypt.hashpw(request.getPin(), BCrypt.gensalt());

            return Account.builder()
                          .memberId(memberId)
                          .accountNumber(request.getAccountNumber())
                          .bankCode(bankCode)
                          .balance(0L)
                          .pin(hashedPin)
                          .build();
        }
    }
}
