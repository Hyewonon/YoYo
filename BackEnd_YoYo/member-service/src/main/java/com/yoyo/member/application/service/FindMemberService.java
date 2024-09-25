package com.yoyo.member.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.member.adapter.out.persistence.entity.MemberJpaEntity;
import com.yoyo.member.adapter.out.persistence.MemberMapper;
import com.yoyo.member.application.port.in.member.FindMemberCommand;
import com.yoyo.member.application.port.in.member.FindMemberUseCase;
import com.yoyo.member.application.port.out.FindMemberPort;
import com.yoyo.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindMemberService implements FindMemberUseCase {

    private final FindMemberPort findMemberPort;
    private final MemberMapper mapper;

    @Override
    public Member findMember(FindMemberCommand command) {
        MemberJpaEntity entity = findMemberPort.findMemberById(new Member.MemberId(command.getMemberId()));
        return mapper.mapToDomainEntity(entity);
    }
}
