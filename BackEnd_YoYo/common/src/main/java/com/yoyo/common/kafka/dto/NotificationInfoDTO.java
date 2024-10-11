package com.yoyo.common.kafka.dto;

import com.yoyo.common.kafka.KafkaJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * [Notification] -> [MemberEvent] 등록 상태 업데이트
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationInfoDTO implements KafkaJson {

    private Long memberId;
    private Long eventId;

    public static NotificationInfoDTO of(Long memberId, Long eventId) {
        return NotificationInfoDTO.builder()
                                  .memberId(memberId)
                                  .eventId(eventId)
                                  .build();
    }
}
