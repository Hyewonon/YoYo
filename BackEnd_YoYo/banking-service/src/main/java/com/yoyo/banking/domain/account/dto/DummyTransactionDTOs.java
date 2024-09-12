package com.yoyo.banking.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "[ DummyTransactionDTO ] 싸피 계좌 거래내역 응답 DTO")
public class DummyTransactionDTOs {

    public static DummyTransactionDTOs of(Long totalCount, List<DummyTransactionDTO> transactions) {
        return DummyTransactionDTOs.builder()
                .totalCount(totalCount)
                .transactions(transactions)
                .build();
    }

    @Data
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DummyTransactionDTO {
        private Long transactionUniqueNo;
        private LocalDateTime transactionDateTime;
        private String transactionTypeName;
        private String transactionSummary;
    }

    private Long totalCount;
    @Builder.Default
    private List<DummyTransactionDTO> transactions = new ArrayList<>();
}
