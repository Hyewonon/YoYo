package com.yoyo.eureka.domain.member_event.service;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.EventException;
import com.yoyo.eureka.domain.event.repository.EventRepository;
import com.yoyo.eureka.domain.member_event.dto.MemberEventCreateDTO;
import com.yoyo.eureka.domain.member_event.dto.MemberEventDTO;
import com.yoyo.eureka.domain.member_event.repository.MemberEventRepository;
import com.yoyo.eureka.entity.Event;
import com.yoyo.eureka.entity.MemberEvent;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberEventService {

    private final MemberEventRepository memberEventRepository;
    private final EventRepository eventRepository;

    public List<MemberEventDTO.Response> getScheduleList(Long memberId) {
        return memberEventRepository.customFindAllByMemberId(memberId,
                                                             LocalDateTime.now());
    }

    public MemberEventDTO.Response getSchedule(Long memberId, Long memberEventId) {
        MemberEvent memberEvent = findMemberEventById(memberEventId);
        isMyMemberEvent(memberEvent, memberId);
        return MemberEventDTO.Response.of(memberEvent);
    }

    public MemberEventCreateDTO createSchedule(Long memberId, MemberEventCreateDTO request) {
        Event event = findEventById(request.getEventId());
        if (memberEventRepository.existsByMemberIdAndEventId(request.getMemberId(), request.getEventId())) {
            throw new EventException(ErrorCode.DUPLICATE_MEMBER_EVENT);
        }

        MemberEvent memberEvent = MemberEventCreateDTO.toEntity(memberId, event);
        return MemberEventCreateDTO.of(memberEventRepository.save(memberEvent));
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
