package com.yoyo.transaction.domain.transaction.dto;

import com.yoyo.common.util.ValidEnum;
import com.yoyo.transaction.entity.TransactionType;
import com.yoyo.transaction.entity.RelationType;
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
        @ValidEnum(enumClass = TransactionType.class)
        private String transactionType;
        @Schema(description = "상대방 id (검색되지 않은 사람일 경우 0)")
        private Long memberId;
        @Schema(description = "상대방 이름 (검색된 사람일 경우 x)")
        private String name;

        @Schema(description = "관계 태그 (검색된 사람일 경우 x)")
        @ValidEnum(enumClass = RelationType.class)
        private String relationType;
        @Schema(description = "관계 설명 (검색된 사람일 경우 x)")
        private String description;

        @Schema(description = "이벤트 id (검색되지 않은 이벤트일 경우 0)")
        private Long eventId;
        @Schema(description = "이벤트 명 (검색된 이벤트일 경우 x)")
        private String eventName;
        private Long amount;
        @Schema(description = "거래 설명")
        private String memo;
    }
}
