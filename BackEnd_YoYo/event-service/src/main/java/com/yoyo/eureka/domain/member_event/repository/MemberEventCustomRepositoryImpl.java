package com.yoyo.eureka.domain.member_event.repository;

import static com.yoyo.eureka.entity.QEvent.event;
import static com.yoyo.eureka.entity.QMemberEvent.memberEvent;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoyo.eureka.domain.member_event.dto.MemberEventDTO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberEventCustomRepositoryImpl implements MemberEventCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberEventDTO.Response> customFindAllByMemberId(Long memberId, LocalDateTime now) {
        return queryFactory
                .select(Projections.constructor(MemberEventDTO.Response.class,
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
