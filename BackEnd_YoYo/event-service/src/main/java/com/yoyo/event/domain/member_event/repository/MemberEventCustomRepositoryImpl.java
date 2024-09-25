package com.yoyo.event.domain.member_event.repository;

import static com.yoyo.event.entity.QEvent.event;
import static com.yoyo.event.entity.QMemberEvent.memberEvent;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoyo.event.domain.member_event.dto.MemberEventDTO.Response;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberEventCustomRepositoryImpl implements MemberEventCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Response> customFindAllByMemberId(Long memberId, LocalDateTime now) {
        return queryFactory
                .select(Projections.constructor(Response.class,
                                                memberEvent.memberId,
                                                event.name,
                                                event.id,
                                                event.title,
                                                event.location,
                                                event.startAt,
                                                event.endAt))
                .from(memberEvent)
                .join(memberEvent.event, event)
                .where(
                        memberEvent.memberId.eq(memberId),
                        event.startAt.goe(now)
                )
                .fetch();
    }
}
