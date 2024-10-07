package com.yoyo.event.domain.event.consumer;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.kafka.dto.*;
import com.yoyo.event.domain.event.producer.EventProducer;
import com.yoyo.event.domain.event.repository.EventRepository;
import com.yoyo.event.domain.event.service.EventService;
import com.yoyo.event.entity.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {

    private final EventService eventService;
    private final EventProducer eventProducer;

    private final String SEND_EVENTID_EVENT_TOPIC = "send-event-id-event-topic";
    private final EventRepository eventRepository;

    @KafkaListener(topics = "transaction-summary-topic", concurrency = "3")
    public void getTransactionSummary(AmountResponseDTO summary) {
        eventService.completeTransactionSummary(summary);
    }

    @KafkaListener(topics = "member-name-topic", concurrency = "3")
    public void getTransactionSummary(MemberResponseDTO name) {
        eventService.completeMemberName(name);
    }

    @KafkaListener(topics = "send-relation-id-list", concurrency = "3")
    public void getTransactionSummary(RelationResponseDTO relation) {
        eventService.completeRelationId(relation);
    }

    @KafkaListener(topics = SEND_EVENTID_EVENT_TOPIC, concurrency = "3")
    public void getEventNameByEventId(EventRequestDTO request) {
        String eventName = eventService.findEventById(request.getEventId());
        eventProducer.sendEventNameToTransaction(EventResponseDTO.of(request.getEventId(), eventName));
    }

    @KafkaListener(topics = "eventId-receiverId", concurrency = "3")
    public void getEventIdByReceiverId(ReceiverRequestDTO requestDTO) {
        Event event = eventRepository.findById(requestDTO.getEventId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EVENT));
        ReceiverRequestDTO updateDTO = ReceiverRequestDTO.builder()
                .eventId(event.getId())
                .receiverId(event.getMemberId())
                .eventName(event.getName())
                .build();
        eventProducer.sendReceiverId(updateDTO);
    }
}
