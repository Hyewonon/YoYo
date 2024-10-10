package com.yoyo.common.kafka.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yoyo.common.kafka.KafkaJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
@Builder
public class EventInfoResponseDTO implements KafkaJson {

    private Long eventId;
    private String eventTitle;

    private Long oppositeId;
    private String oppositeName;

    public static EventInfoResponseDTO of(Long eventId, String eventTitle, Long oppositeId, String oppositeName) {
        return EventInfoResponseDTO.builder()
                                   .eventId(eventId)
                                   .eventTitle(eventTitle)
                                   .oppositeId(oppositeId)
                                   .oppositeName(oppositeName)
                                   .build();
    }
}
