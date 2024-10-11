package com.yoyo.event.domain.member_event.dto;

import com.yoyo.event.entity.Event;
import com.yoyo.event.entity.MemberEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberEventCreateDTO {

    private Long memberId;
    private Long eventId;

    public static MemberEvent toEntity(Long memberId, Event event) {
        return MemberEvent.builder()
                          .memberId(memberId)
                          .event(event).build();
    }

    public static MemberEventCreateDTO of(MemberEvent memberEvent) {
        return MemberEventCreateDTO.builder()
                                   .memberId(memberEvent.getMemberId())
                                   .eventId(memberEvent.getEvent().getId())
                                   .build();
    }
}