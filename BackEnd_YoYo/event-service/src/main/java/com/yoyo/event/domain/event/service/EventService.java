package com.yoyo.event.domain.event.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.EventException;
import com.yoyo.common.kafka.dto.AmountRequestDTO;
import com.yoyo.common.kafka.dto.AmountResponseDTO;
import com.yoyo.common.kafka.dto.EventMemberResponseDTO;
import com.yoyo.common.kafka.dto.EventMemberRequestDTO;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;
    private final int PAGE_SIZE = 10;
    private final Map<Long, CompletableFuture<AmountResponseDTO>> summaries = new ConcurrentHashMap<>();
    private final Map<Long, CompletableFuture<EventMemberResponseDTO>> names = new ConcurrentHashMap<>();

    public EventDTO.Response createEvent(Long memberId, EventDTO.Request request) {
        EventMemberRequestDTO message = new EventMemberRequestDTO(memberId);
        eventProducer.getMemberName(message);
        CompletableFuture<EventMemberResponseDTO> future = new CompletableFuture<>();
        names.put(memberId, future);
        String name;
        try{
            name = future.get(10, TimeUnit.SECONDS).getName();
        } catch (Exception e){
            throw new RuntimeException("Failed Kafka", e);
        }

        // TODO : sendLink 생성 로직
        // 링크 눌렀을 때 - eventId, title, memberId, memberName 함께 반환
        String sendLink = "";
        Event event = EventDTO.Request.toEntity(request, memberId, name, sendLink);
        return EventDTO.Response.of(eventRepository.save(event));
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

        return EventDetailDTO.Response.of(event.getId(), totalReceiver, totalReceivedAmount);
    }

    public EventDTO.Response updateEvent(Long memberId, Long eventId, EventUpdateDTO.Request request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND));
        isMyEvent(event, memberId);

        event.updateEvent(request.getTitle(), request.getLocation(), request.getStartAt(), request.getEndAt());
        return EventDTO.Response.of(eventRepository.save(event));
    }

    public Slice<EventDTO.Response> searchEvent(Long memberId, String keyword, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("title"));
        return eventRepository.searchEventByTitle(memberId, keyword, pageable);
    }

    public void completeTransactionSummary(AmountResponseDTO summary) {
        CompletableFuture<AmountResponseDTO> future = summaries.remove(summary.getEventId());
        if (future != null) {
            future.complete(summary);
        }
    }

    public void completeMemberName(EventMemberResponseDTO name) {
        CompletableFuture<EventMemberResponseDTO> future = names.remove(name.getMemberId());
        if (future != null) {
            future.complete(name);
        }
    }

    private void isMyEvent(Event event, Long memberId){
        if(!event.getMemberId().equals(memberId)) throw new EventException(ErrorCode.FORBIDDEN_EVENT);
    }
}
