package com.yoyo.event.domain.event.repository;

import com.yoyo.event.domain.event.dto.EventDTO.Response;
import java.util.List;

public interface EventCustomRepository {

    List<Response> searchEventByTitle(Long memberId, String keyword);

}
