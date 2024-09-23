package com.yoyo.eureka.domain.event.dto;

import com.yoyo.eureka.entity.Event;
import com.yoyo.eureka.entity.EventType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class EventDTO {

    @Getter
    public static class Request {

        private String eventType;
        private String title;
        private String location;
        private LocalDateTime startAt;
        private LocalDateTime endAt;

        public static Event toEntity(EventDTO.Request request, Long memberId, String name, String sendLink) {
            return Event.builder()
                        .memberId(memberId)
                        .name(name)
                        .eventType(EventType.valueOf(request.getEventType().toUpperCase()))
                        .title(request.getTitle())
                        .location(request.getLocation())
                        .startAt(request.getStartAt())
                        .endAt(request.getEndAt())
                        .sendLink(sendLink).build();
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private Long eventId;
        private String title;
        private String location;
        private LocalDateTime startAt;
        private LocalDateTime endAt;

        public static Response of(Event event) {
            return Response.builder()
                           .eventId(event.getId())
                           .title(event.getTitle())
                           .location(event.getLocation())
                           .startAt(event.getStartAt())
                           .endAt(event.getEndAt()).build();
        }
    }
}
