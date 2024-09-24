package com.yoyo.banking.domain.account.dto.pay;

import com.yoyo.banking.entity.PayTransaction;
import com.yoyo.banking.entity.PayType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PayTransactionDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[ PayTransactionDTO ] 페이 거래내역 조회 응답 DTO")
    public static class Response {

        private PayType payType;

        private Long payAmount;

        private String createdAt;

        private String name;

        public static Response of(PayTransaction payTransaction) {
            return Response.builder()
                    .payType(payTransaction.getPayType())
                    .payAmount(payTransaction.getPayAmount())
                    .createdAt(payTransaction.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                    .name(payTransaction.getName())
                    .build();
        }
    }
}
