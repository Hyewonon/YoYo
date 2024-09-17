package com.yoyo.event.domain.event.repository;

import com.yoyo.event.domain.event.dto.EventDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface EventCustomRepository {

    Slice<EventDTO.Response> searchEventByTitle(Long memberId, String keyword, Pageable pageable);

}
