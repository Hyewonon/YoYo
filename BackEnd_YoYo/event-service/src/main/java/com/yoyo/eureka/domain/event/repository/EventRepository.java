package com.yoyo.eureka.domain.event.repository;

import com.yoyo.eureka.entity.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>, EventCustomRepository {

    List<Event> findAllByMemberId(Long memberId);
}
