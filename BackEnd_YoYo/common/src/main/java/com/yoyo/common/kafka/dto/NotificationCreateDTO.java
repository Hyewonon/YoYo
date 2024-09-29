package com.yoyo.common.kafka.dto;

import com.yoyo.common.kafka.KafkaJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationCreateDTO implements KafkaJson {

    Long senderId;
    String name;
    Long receiverId;
    Long eventId;
    String title;
    String type;

    public static NotificationCreateDTO of(Long senderId, String name, Long receiverId, Long eventId, String title,
                                           String type) {
        return NotificationCreateDTO.builder()
                                    .senderId(senderId)
                                    .name(name)
                                    .receiverId(receiverId)
                                    .eventId(eventId)
                                    .title(title)
                                    .type(type)
                                    .build();
    }
}
