package com.yoyo.event.domain.member_event.dto;

import com.yoyo.event.entity.Event;
import com.yoyo.event.entity.MemberEvent;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberEventDTO {

    @Getter
    public static class Request {

        private Long memberId;

        public static MemberEvent toEntity(Long memberId, Event event) {
            return MemberEvent.builder()
                              .memberId(memberId)
                              .event(event).build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private Long memberId;
        private String name;
        private Long eventId;
        private String title;
        private String location;
        private LocalDateTime startAt;
        private LocalDateTime endAt;

        public static MemberEventDTO.Response of(MemberEvent memberEvent) {
            return Response.builder()
                           .memberId(memberEvent.getEvent().getMemberId())
                           .name(memberEvent.getEvent().getName())
                           .eventId(memberEvent.getEvent().getId())
                           .title(memberEvent.getEvent().getTitle())
                           .location(memberEvent.getEvent().getLocation())
                           .startAt(memberEvent.getEvent().getStartAt())
                           .endAt(memberEvent.getEvent().getEndAt())
                           .build();
        }
    }
}