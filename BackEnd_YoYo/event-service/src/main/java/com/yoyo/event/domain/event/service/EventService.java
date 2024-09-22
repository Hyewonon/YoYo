package com.yoyo.event.domain.event.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.EventException;
import com.yoyo.event.domain.event.dto.EventDTO;
import com.yoyo.event.domain.event.dto.EventDetailDTO;
import com.yoyo.event.domain.event.dto.EventUpdateDTO;
import com.yoyo.event.domain.event.producer.TransactionSummaryRequest;
import com.yoyo.event.domain.event.producer.EventProducer;
import com.yoyo.event.domain.event.consumer.TransactionSummaryResponse;
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
    private final EventProducer eventToTransactionProducer;
    private final int PAGE_SIZE = 10;
    private final Map<Long, CompletableFuture<TransactionSummaryResponse>> summaries = new ConcurrentHashMap<>();


    public EventDTO.Response createEvent(Long memberId, EventDTO.Request request) {
        // TODO : [Member] 유효 검증 로직
        // TODO : sendLink 생성 로직
        String sendLink = "";
        Event event = EventDTO.Request.toEntity(request, memberId, sendLink);
        return EventDTO.Response.of(eventRepository.save(event));
    }

    public List<EventDTO.Response> getEventList(Long memberId) {
        return eventRepository.findAllByMemberId(memberId).stream()
                .map(EventDTO.Response::of)
                .collect(Collectors.toList());
    }


    public EventDetailDTO.Response getEvent(Long memberId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND));
        // TODO : [Member] 유효 검증 로직 -> 나의 이벤트인가
        // TODO : [Transactional] EventId와 주최자(memberId)로 보낸 사람 수, 받은 총액 가져오기
        TransactionSummaryRequest message = new TransactionSummaryRequest(memberId, eventId);
        eventToTransactionProducer.sendEventId(message);
        CompletableFuture<TransactionSummaryResponse> future = new CompletableFuture<>();
        summaries.put(eventId, future);
        TransactionSummaryResponse summary;
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

        // TODO : [Member] 유효 검증 로직 -> 나의 이벤트인가

        event.updateEvent(request.getTitle(), request.getLocation(), request.getStartAt(), request.getEndAt());
        return EventDTO.Response.of(eventRepository.save(event));
    }

    public Slice<EventDTO.Response> searchEvent(Long memberId, String keyword, int pageNumber) {
        // TODO : [Member] 유효한 멤버인지 검증

        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("title"));
        return eventRepository.searchEventByTitle(memberId, keyword, pageable);
    }

    public void completeTransactionSummary(TransactionSummaryResponse summary) {
        CompletableFuture<TransactionSummaryResponse> future = summaries.remove(summary.getEventId());
        if (future != null) {
            future.complete(summary);
        }
    }
}
