package com.yoyo.member.application.service;

import com.yoyo.common.UseCase;
import com.yoyo.member.application.port.in.FindMemberCommand;
import com.yoyo.member.application.port.in.FindMemberUseCase;
import com.yoyo.member.application.port.out.FindMemberPort;
import com.yoyo.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindMemberService implements FindMemberUseCase {

    private final FindMemberPort findMemberPort;

    @Override
    public Member findMember(FindMemberCommand command) {
        return findMemberPort.findMember(new Member.MemberId(command.getMemberId()));
    }
}
