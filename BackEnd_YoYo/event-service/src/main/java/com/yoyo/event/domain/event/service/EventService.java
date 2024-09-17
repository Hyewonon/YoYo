package com.yoyo.event.domain.event.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.EventException;
import com.yoyo.event.domain.event.dto.EventDTO;
import com.yoyo.event.domain.event.dto.EventDetailDTO;
import com.yoyo.event.domain.event.dto.EventUpdateDTO;
import com.yoyo.event.domain.event.dto.TransactionDTO;
import com.yoyo.event.domain.event.repository.EventRepository;
import com.yoyo.event.entity.Event;
import java.util.ArrayList;
import java.util.List;
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
    private final int PAGE_SIZE = 10;

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

    public EventDetailDTO.Response getEvent(Long memberId, Long eventId, String tag, boolean isRegister) {
        Event event = eventRepository.findById(eventId)
                                     .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND));

        // TODO : [Member] 유효 검증 로직 -> 나의 이벤트인가

        // TODO : [Transactional] EventId로 받은 사람, 받은 총액 가져오기
        Long totalReceiver = 100L;
        Long totalReceivedAmount = 100L;

        // TODO : [Transactional] EventId & 필터링 정보로 거래 내역 가져오기
        List<TransactionDTO.Response> transactions = new ArrayList<>();
        if (isRegister) {
        } else {
        }
        return EventDetailDTO.Response.of(event.getId(), totalReceiver, totalReceivedAmount, transactions);
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
}
