package com.yoyo.event.domain.event.repository;

import static com.yoyo.event.entity.QEvent.event;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoyo.event.domain.event.dto.EventDTO.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class EventCustomRepositoryImpl implements EventCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Response> searchEventByTitle(Long memberId, String keyword, Pageable pageable) {
        List<Response> results = queryFactory
                .select(Projections.constructor(Response.class,
                                                event.id,
                                                event.title,
                                                event.location,
                                                event.startAt,
                                                event.endAt))
                .from(event)
                .where(
                        event.memberId.eq(memberId),
                        event.title.contains(keyword)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(event.title.asc())
                .fetch();

        return new SliceImpl<>(results, pageable, hasNextPage(results, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<Response> results, int pageSize) {
        if (results.size() > pageSize) {
            results.remove(pageSize);
            return true;
        }
        return false;
    }
}
