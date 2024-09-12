package com.yoyo.event.domain.member_event.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberDTO {

    @Getter
    @Builder
    public static class Response {

        private Long memberId;
        private String name;

        public static MemberDTO.Response of(Long memberId, String name) {
            return Response.builder()
                           .memberId(memberId)
                           .name(name)
                           .build();
        }
    }
}