package com.yoyo.member.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.member.adapter.out.persistence.MemberJpaEntity;
import com.yoyo.member.adapter.out.persistence.MemberMapper;
import com.yoyo.member.application.port.in.member.RegisterMemberCommand;
import com.yoyo.member.application.port.in.member.RegisterMemberUseCase;
import com.yoyo.member.application.port.out.RegisterMemberPort;
import com.yoyo.member.domain.Member;
import com.yoyo.member.domain.Member.MemberBirthDay;
import com.yoyo.member.domain.Member.MemberIsValid;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

@RequiredArgsConstructor
@Transactional
@UseCase
public class RegisterMemberService implements RegisterMemberUseCase {

    private final RegisterMemberPort registerMemberPort;
    private final MemberMapper memberMapper;
    @Override
    public Member registerMember(RegisterMemberCommand command) {
        String hashedPassword = BCrypt.hashpw(command.getPassword(), BCrypt.gensalt());
        if (!command.isValid()) {
            throw new IllegalArgumentException("전화번호 미인증");
        }
        // command -> DB
        // port, adapter
        MemberJpaEntity jpaEntity = registerMemberPort.createMember(
                new Member.MemberName(command.getName()),
                new Member.MemberPhoneNumber(command.getPhoneNumber()),
                new Member.MemberPassword(hashedPassword),
                new MemberBirthDay(command.getBirthDay()),
                new MemberIsValid(command.isValid())
        );

        return memberMapper.mapToDomainEntity(jpaEntity);
    }
}
