package com.yoyo.event.domain.member_event.consumer;

import com.yoyo.common.kafka.dto.NotificationInfoDTO;
import com.yoyo.event.domain.member_event.service.MemberEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberEventConsumer {

    private final MemberEventService memberEventService;
    private final String CREATE_MEMBER_EVENT = "create-member-event-topic";

    @KafkaListener(topics = CREATE_MEMBER_EVENT, concurrency = "3")
    public void createMemberEvent(NotificationInfoDTO request) {
        memberEventService.createSchedule(request.getMemberId(), request.getEventId());
    }

}
