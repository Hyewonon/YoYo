package com.yoyo.event.domain.member_event.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.EventException;
import com.yoyo.event.domain.event.repository.EventRepository;
import com.yoyo.event.domain.member_event.dto.MemberEventCreateDTO;
import com.yoyo.event.domain.member_event.dto.MemberEventDTO;
import com.yoyo.event.domain.member_event.dto.MemberEventDTO.Response;
import com.yoyo.event.domain.member_event.repository.MemberEventRepository;
import com.yoyo.event.entity.Event;
import com.yoyo.event.entity.MemberEvent;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberEventService {

    private final MemberEventRepository memberEventRepository;
    private final EventRepository eventRepository;

    public List<Response> getScheduleList(Long memberId) {
        return memberEventRepository.customFindAllByMemberId(memberId,
                                                             LocalDateTime.now());
    }

    public Response getSchedule(Long memberId, Long memberEventId) {
        MemberEvent memberEvent = findMemberEventById(memberEventId);
        isMyMemberEvent(memberEvent, memberId);
        return MemberEventDTO.Response.of(memberEvent);
    }

    public MemberEventCreateDTO.Response createSchedule(Long memberId, MemberEventCreateDTO.Request request) {
        Event event = findEventById(request.getEventId());
        if (memberEventRepository.existsByMemberIdAndEventId(memberId, request.getEventId())) {
            throw new EventException(ErrorCode.DUPLICATE_MEMBER_EVENT);
        }

        MemberEvent memberEvent = MemberEventCreateDTO.Request.toEntity(memberId, event);
        return MemberEventCreateDTO.Response.of(memberEventRepository.save(memberEvent));
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                              .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND));
    }

    private MemberEvent findMemberEventById(Long memberEventId) {
        return memberEventRepository.findById(memberEventId)
                                    .orElseThrow(() -> new EventException(ErrorCode.NOT_FOUND));
    }

    private void isMyMemberEvent(MemberEvent memberEvent, Long memberId) {
        if (!memberEvent.getMemberId().equals(memberId)) {
            throw new EventException(ErrorCode.FORBIDDEN_EVENT);
        }
    }
}
