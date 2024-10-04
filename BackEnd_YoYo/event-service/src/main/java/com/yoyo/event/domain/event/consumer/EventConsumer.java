package com.yoyo.event.domain.event.consumer;

import com.yoyo.common.kafka.dto.*;
import com.yoyo.event.domain.event.producer.EventProducer;
import com.yoyo.event.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {

    private final EventService eventService;
    private final EventProducer eventProducer;

    private final String SEND_EVENTID_EVENT_TOPIC = "send-event-id-event-topic";

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
        requestDTO.setReceiverId(eventService.findEventByIdReceiverId(requestDTO.getReceiverId()));
        eventProducer.sendReceiverId(requestDTO);
    }
}
