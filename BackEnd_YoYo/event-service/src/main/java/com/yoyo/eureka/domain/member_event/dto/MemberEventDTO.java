package com.yoyo.eureka.domain.member_event.dto;

import com.yoyo.eureka.entity.Event;
import com.yoyo.eureka.entity.MemberEvent;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

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
    public static class Response {

        private Long memberId;
        private String name;
        private Long eventId;
        private String title;
        private String location;
        private LocalDateTime startAt;
        private LocalDateTime endAt;

        @Builder
        public Response(Long memberId, String name, Long eventId, String title, String location, LocalDateTime startAt, LocalDateTime endAt) {
            this.memberId = memberId;
            this.name = name;
            this.eventId = eventId;
            this.title = title;
            this.location = location;
            this.startAt = startAt;
            this.endAt = endAt;
        }

        public Response(Long memberId, Long eventId, String title, String location, LocalDateTime startAt, LocalDateTime endAt) {
            this.memberId = memberId;
            this.eventId = eventId;
            this.title = title;
            this.location = location;
            this.startAt = startAt;
            this.endAt = endAt;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static MemberEventDTO.Response of(MemberEvent memberEvent) {
            return Response.builder()
                           .memberId(memberEvent.getMemberId())
                           .eventId(memberEvent.getEvent().getId())
                           .title(memberEvent.getEvent().getTitle())
                           .location(memberEvent.getEvent().getLocation())
                           .startAt(memberEvent.getEvent().getStartAt())
                           .endAt(memberEvent.getEvent().getEndAt())
                           .build();
        }
    }
}