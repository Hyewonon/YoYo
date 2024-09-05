package com.yoyo.member.application.service;

import com.yoyo.common.UseCase;
import com.yoyo.member.application.port.in.UpdateMemberCommand;
import com.yoyo.member.application.port.in.UpdateMemberUseCase;
import com.yoyo.member.application.port.out.UpdateMemberPort;
import com.yoyo.member.domain.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class UpdateMemberService implements UpdateMemberUseCase {

    private final UpdateMemberPort updateMemberPort;


    @Override
    public void updateMember(UpdateMemberCommand command) {
        Member member = updateMemberPort.findMemberById(command.getMemberId());
        member.updateName(new Member.MemberName(command.getName()));
        updateMemberPort.save(member);
    }
}
