package com.yoyo.member.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.member.adapter.out.persistence.MemberJpaEntity;
import com.yoyo.member.adapter.out.persistence.MemberMapper;
import com.yoyo.member.application.port.in.UpdateMemberCommand;
import com.yoyo.member.application.port.in.UpdateMemberUseCase;
import com.yoyo.member.application.port.out.UpdateMemberPort;
import com.yoyo.member.domain.Member;
import com.yoyo.member.domain.Member.MemberBirthDay;
import com.yoyo.member.domain.Member.MemberRefreshToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class UpdateMemberService implements UpdateMemberUseCase {

    private final UpdateMemberPort updateMemberPort;
    private final MemberMapper mapper;


    @Override
    public Member updateMember(UpdateMemberCommand command) {
        MemberJpaEntity memberJpaEntity = updateMemberPort.updateMember(
                new Member.MemberId(command.getMemberId()),
                new Member.MemberName(command.getName()),
                new Member.MemberPhoneNumber(command.getPhoneNumber()),
                new Member.MemberBirthDay(command.getBirthDay()),
                new MemberRefreshToken("")
        );
        return mapper.mapToDomainEntity(memberJpaEntity);
    }
}
