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
public class EventResponseDTO implements KafkaJson {

    private Long eventId;
    private String name;

    public static EventResponseDTO of(Long eventId, String name){
        return EventResponseDTO.builder()
                               .eventId(eventId)
                               .name(name)
                               .build();
    }
}
