package com.yoyo.event.global.util;

import com.yoyo.event.domain.event.repository.EventRepository;
import com.yoyo.event.domain.member_event.repository.MemberEventRepository;
import com.yoyo.event.entity.Event;
import com.yoyo.event.entity.EventType;
import com.yoyo.event.entity.MemberEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final EventRepository eventRepository;
    private final MemberEventRepository memberEventRepository;
    public static List<Event> generateDummyEvents() {
        List<Event> events = new ArrayList<>();

        Event event1 = Event.builder()
                .memberId(2L)
                .name("결혼식")
                .eventType(EventType.WEDDING)
                .title("결혼식")
                .location("서울 역삼")
                .startAt(LocalDateTime.now().plusDays(5))
                .endAt(LocalDateTime.now().plusDays(7 + 1))
                .memberEvents(new ArrayList<>())
                .build();
        Event event2 = Event.builder()
                .memberId(3L)
                .name("결혼식")
                .eventType(EventType.WEDDING)
                .title("결혼식")
                .location("서울 역삼")
                .startAt(LocalDateTime.now().plusDays(5))
                .endAt(LocalDateTime.now().plusDays(7 + 1))
                .memberEvents(new ArrayList<>())
                .build();
        Event event3 = Event.builder()
                .memberId(1L)
                .name("결혼식")
                .eventType(EventType.WEDDING)
                .title("결혼식")
                .location("서울 역삼")
                .startAt(LocalDateTime.now().plusDays(5))
                .endAt(LocalDateTime.now().plusDays(7 + 1))
                .memberEvents(new ArrayList<>())
                .build();

        events.add(event1);
        events.add(event2);
        events.add(event3);

        return events;
    }

    public static List<MemberEvent> generateDummyMemberEvents(List<Event> events) {
        List<MemberEvent> memberEvents = new ArrayList<>();

        MemberEvent memberEvent1 = MemberEvent.builder()
                .memberId(2L)
                .event(events.get(0))
                .build();
        MemberEvent memberEvent2 = MemberEvent.builder()
                .memberId(3L)
                .event(events.get(1))
                .build();
        MemberEvent memberEvent3 = MemberEvent.builder()
                .memberId(1L)
                .event(events.get(2))
                .build();

        memberEvent1.setEvent(events.get(0));
        memberEvent2.setEvent(events.get(1));
        memberEvent3.setEvent(events.get(2));
        memberEvents.add(memberEvent1);
        memberEvents.add(memberEvent2);
        memberEvents.add(memberEvent3);

        return memberEvents;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (eventRepository.count() == 0) {
        List<Event> events = generateDummyEvents();
        eventRepository.saveAll(events);
        List<MemberEvent> memberEvents = generateDummyMemberEvents(events);
        memberEventRepository.saveAll(memberEvents);
        }
    }
}
