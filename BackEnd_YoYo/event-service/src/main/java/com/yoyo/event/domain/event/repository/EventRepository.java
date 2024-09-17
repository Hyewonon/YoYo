package com.yoyo.event.domain.event.repository;

import com.yoyo.event.entity.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventCustomRepository {

    List<Event> findAllByMemberId(Long memberId);
}
