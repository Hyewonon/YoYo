package com.yoyo.banking.domain.account.dto.pay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PayTransferDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[ PayTransferDTO ] 페이 거래용 요청 DTO")
    public static class Request {

        private Long eventId;

        private Long payAmount;
    }
}
