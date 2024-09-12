package com.yoyo.event.domain.member_event.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.EventException;
import com.yoyo.event.domain.event.dto.TransactionDTO;
import com.yoyo.event.domain.event.repository.EventRepository;
import com.yoyo.event.domain.member_event.dto.MemberEventCreateDTO;
import com.yoyo.event.domain.member_event.dto.MemberEventDTO;
import com.yoyo.event.domain.member_event.dto.MemberEventTransactionDTO;
import com.yoyo.event.domain.member_event.repository.MemberEventRepository;
import com.yoyo.event.entity.Event;
import com.yoyo.event.entity.MemberEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberEventService {

    private final MemberEventRepository memberEventRepository;
    private final EventRepository eventRepository;

    private final String name = "홍길동"; // 임시 이름

    public List<MemberEventDTO.Response> getScheduleList(Long memberId) {
        List<MemberEventDTO.Response> memberEvents = memberEventRepository.customFindAllByMemberId(memberId,
                                                                                                   LocalDateTime.now());

        return memberEvents.stream()
                           .map(memberEvent -> {
                               // TODO : [Member] memberEvent.event.memberId로 name 조회
                               memberEvent.setName(name);
                               return memberEvent;
                           })
                           .collect(Collectors.toList());
    }

    public MemberEventTransactionDTO.Response getSchedule(Long memberId, Long memberEventId, String type) {
        MemberEvent memberEvent = memberEventRepository.findById(memberEventId)
                                                       .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND));

        // TODO : [Member] memberEvent.event.memberId로 name 조회

        List<TransactionDTO.Response> transactions = new ArrayList<>();
        // TODO : [Transaction] senderId: memberId, receiverId: event.memberId
        Long totalSentAmount = 100L;
        // TODO : [Transaction] senderId: event.memberId, receiverId: memberId
        Long totalReceivedAmount = 100L;

        return MemberEventTransactionDTO.Response.of(name, memberEvent.getEvent(), totalSentAmount, totalReceivedAmount,
                                                     transactions);
    }

    public MemberEventCreateDTO createSchedule(Long memberId, MemberEventCreateDTO request) {
        if (memberEventRepository.existsByMemberIdAndEventId(request.getMemberId(), request.getEventId())) {
            throw new EventException(ErrorCode.DUPLICATE_MEMBER_EVENT);
        }

        Event event = eventRepository.findById(request.getEventId())
                                     .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND));

        MemberEvent memberEvent = MemberEventCreateDTO.toEntity(memberId, event);
        return MemberEventCreateDTO.of(memberEventRepository.save(memberEvent));
    }
}
