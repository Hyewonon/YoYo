package com.yoyo.transaction.domain.transaction.dto;

import com.yoyo.transaction.entity.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TransactionCreateDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[TransactionCreateDTO] 요요 거래내역 직접등록 요청 DTO")
    public static class Request {

        @Schema(description = "거래 종류 (받는사람 등록 시 RECEIVE, 보낸사람이 등록 시 SEND)")
        private TransactionType transactionType;
        @Schema(description = "상대방 id")
        private Long memberId;
        @Schema(description = "상대방 이름")
        private String name;

        @Schema(description = "관계 태그")
        private String relationType;
        @Schema(description = "관계 설명")
        private String description;
        private Long eventId;
        private String eventName;
        private Long amount;
        @Schema(description = "거래 설명")
        private String memo;
    }

}
