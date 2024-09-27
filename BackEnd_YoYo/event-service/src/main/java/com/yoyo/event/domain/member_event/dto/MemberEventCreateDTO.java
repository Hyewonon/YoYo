package com.yoyo.event.domain.member_event.dto;

import com.yoyo.event.entity.Event;
import com.yoyo.event.entity.MemberEvent;
import lombok.Builder;
import lombok.Getter;


public class MemberEventCreateDTO {

    @Getter
    public static class Request {

        private Long eventId;

        public static MemberEvent toEntity(Long memberId, Event event) {
            return MemberEvent.builder()
                              .memberId(memberId)
                              .event(event).build();
        }
    }

    @Getter
    @Builder
    public static class Response {

        private Long memberId;
        private Long eventId;

        public static MemberEventCreateDTO.Response of(MemberEvent memberEvent) {
            return MemberEventCreateDTO.Response.builder()
                                                .memberId(memberEvent.getMemberId())
                                                .eventId(memberEvent.getEvent().getId())
                                                .build();
        }

    }


}