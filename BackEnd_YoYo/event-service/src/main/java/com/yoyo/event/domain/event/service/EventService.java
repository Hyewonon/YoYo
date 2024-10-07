package com.yoyo.event.domain.event.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.EventException;
import com.yoyo.common.kafka.dto.AmountRequestDTO;
import com.yoyo.common.kafka.dto.AmountResponseDTO;
import com.yoyo.common.kafka.dto.MemberResponseDTO;
import com.yoyo.common.kafka.dto.MemberRequestDTO;
import com.yoyo.common.kafka.dto.NotificationCreateDTO;
import com.yoyo.common.kafka.dto.RelationResponseDTO;
import com.yoyo.event.domain.event.producer.EventProducer;
import com.yoyo.event.domain.event.dto.EventDTO;
import com.yoyo.event.domain.event.dto.EventDetailDTO;
import com.yoyo.event.domain.event.dto.EventDetailDTO.Response;
import com.yoyo.event.domain.event.dto.EventUpdateDTO;
import com.yoyo.event.domain.event.repository.EventRepository;
import com.yoyo.event.entity.Event;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;
    private final Map<Long, CompletableFuture<AmountResponseDTO>> summaries = new ConcurrentHashMap<>();
    private final Map<Long, CompletableFuture<MemberResponseDTO>> names = new ConcurrentHashMap<>();
    private final Map<Long, CompletableFuture<RelationResponseDTO>> relationIds = new ConcurrentHashMap<>();

    public EventDTO.Response createEvent(Long memberId, EventDTO.Request request) {
        // MyName Get
        MemberRequestDTO message = new MemberRequestDTO(memberId);
        eventProducer.getMemberName(message);
        CompletableFuture<MemberResponseDTO> future = new CompletableFuture<>();
        names.put(memberId, future);
        String name;
        try{
            name = future.get(10, TimeUnit.SECONDS).getName();
        } catch (Exception e){
            throw new RuntimeException("Failed Kafka", e);
        }
        
        // Event 생성
        Event event = EventDTO.Request.toEntity(request, memberId, name);
        Event savedEvent = eventRepository.save(event);

        // Relation 리스트 Get
        eventProducer.getRelationIds(message);
        CompletableFuture<RelationResponseDTO> relationFuture = new CompletableFuture<>();
        relationIds.put(memberId, relationFuture);
        List<Long> IdList;
        try{
            IdList = relationFuture.get(10, TimeUnit.SECONDS).getRelationIds();
        } catch (Exception e){
            throw new RuntimeException("Failed Kafka", e);
        }

        // Relation Member에게 알림 생성
        for(Long receiverId : IdList){
            eventProducer.sendEventNotification(
                    NotificationCreateDTO.of(memberId, name, receiverId, savedEvent.getId(),
                                             savedEvent.getTitle(), "EVENT"));
        }
        return EventDTO.Response.of(savedEvent);
    }

    public List<EventDTO.Response> getEventList(Long memberId) {
        return eventRepository.findAllByMemberId(memberId).stream()
                .map(EventDTO.Response::of)
                .collect(Collectors.toList());
    }

    public Response getEvent(Long memberId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND));
        isMyEvent(event, memberId);

        AmountRequestDTO message = new AmountRequestDTO(memberId, eventId);
        eventProducer.sendEventId(message);
        CompletableFuture<AmountResponseDTO> future = new CompletableFuture<>();
        summaries.put(eventId, future);
        AmountResponseDTO summary;
        try {
            summary = future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException("Failed Kafka", e);
        }
        int totalReceiver = summary.getTransactionCount();
        long totalReceivedAmount = summary.getTotalAmount();

        return EventDetailDTO.Response.of(event.getId(), event.getTitle(), totalReceiver, totalReceivedAmount);
    }

    public EventDTO.Response updateEvent(Long memberId, Long eventId, EventUpdateDTO.Request request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND));
        isMyEvent(event, memberId);

        event.updateEvent(request.getTitle(), request.getLocation(), request.getStartAt(), request.getEndAt());
        return EventDTO.Response.of(eventRepository.save(event));
    }

    public List<EventDTO.Response> searchEvent(Long memberId, String keyword) {
//        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("title"));
        return eventRepository.searchEventByTitle(memberId, keyword);
    }

    public void completeTransactionSummary(AmountResponseDTO summary) {
        CompletableFuture<AmountResponseDTO> future = summaries.remove(summary.getEventId());
        if (future != null) {
            future.complete(summary);
        }
    }

    public void completeMemberName(MemberResponseDTO name) {
        CompletableFuture<MemberResponseDTO> future = names.remove(name.getMemberId());
        if (future != null) {
            future.complete(name);
        }
    }

    public void completeRelationId(RelationResponseDTO relation) {
        CompletableFuture<RelationResponseDTO> relationFuture = relationIds.remove(relation.getMemberId());
        if (relationFuture != null) {
            relationFuture.complete(relation);
        }
    }

    private void isMyEvent(Event event, Long memberId){
        if(!event.getMemberId().equals(memberId)) throw new EventException(ErrorCode.FORBIDDEN_EVENT);
    }

    public String findEventById(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND_EVENT));
        return event.getTitle();
    }
}
