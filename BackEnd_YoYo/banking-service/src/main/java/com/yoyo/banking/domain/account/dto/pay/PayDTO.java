package com.yoyo.banking.domain.account.dto.pay;

import com.yoyo.banking.entity.Account;
import com.yoyo.banking.entity.PayTransaction;
import com.yoyo.banking.entity.PayType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PayDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[ PayDTO ] 페이 관련 요청 DTO")
    public static class Request {

        private Long payAmount;
        @Nullable
        private String name;

        public static PayTransaction toEntity(PayDTO.Request request, Long accountId, String name, PayType payType) {
            return PayTransaction.builder()
                                 .accountId(accountId)
                                 .payType(payType)
                                 .payAmount(request.getPayAmount())
                                 .createdAt(LocalDateTime.now())
                                 .name(name)
                                 .build();
        }

        public static PayDTO.Request toDto(Long payAmount, String name) {
            return Request.builder()
                    .payAmount(payAmount)
                    .name(name)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[ PayDTO ] 페이 잔액조회 관련 응답 DTO")
    public static class Response {

        private Long balance;
        private String memberName;

        public static Response of(Account account, String name) {
            return PayDTO.Response.builder()
                                  .balance(account.getBalance())
                                  .memberName(name)
                                  .build();
        }

        public static Response of(String name) {
            return PayDTO.Response.builder()
                                  .balance(-1L)
                                  .memberName(name)
                                  .build();
        }
    }
}
