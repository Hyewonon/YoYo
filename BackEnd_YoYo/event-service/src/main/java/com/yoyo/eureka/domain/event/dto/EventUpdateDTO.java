package com.yoyo.eureka.domain.event.dto;

import java.time.LocalDateTime;
import lombok.Getter;

public class EventUpdateDTO {

    @Getter
    public static class Request {

        private String title;
        private String location;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
    }

}
