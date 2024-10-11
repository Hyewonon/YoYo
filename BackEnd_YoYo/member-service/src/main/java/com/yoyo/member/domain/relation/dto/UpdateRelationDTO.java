package com.yoyo.member.domain.relation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UpdateRelationDTO {

    /*
     * * 친구관계 정보 수정 요청 DTO
     * */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "[ UpdateRelationDTO ] 친구관계 수정 요청 DTO")
    public static class Request {

        private Long memberId;

        private String relationType;

        private String description;
    }
}
