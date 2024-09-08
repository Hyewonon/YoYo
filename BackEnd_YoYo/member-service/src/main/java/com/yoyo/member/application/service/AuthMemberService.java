package com.yoyo.member.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.member.adapter.out.persistence.MemberJpaEntity;
import com.yoyo.member.adapter.out.persistence.MemberMapper;
import com.yoyo.member.application.port.in.AuthMemberUseCase;
import com.yoyo.member.application.port.in.LoginMemberCommand;
import com.yoyo.member.application.port.in.RefreshTokenCommand;
import com.yoyo.member.application.port.in.ValidateTokenCommand;
import com.yoyo.member.application.port.out.AuthMemberPort;
import com.yoyo.member.application.port.out.FindMemberPort;
import com.yoyo.member.application.port.out.UpdateMemberPort;
import com.yoyo.member.domain.JwtToken;
import com.yoyo.member.domain.JwtToken.MemberJwtToken;
import com.yoyo.member.domain.JwtToken.MemberRefreshToken;
import com.yoyo.member.domain.Member;
import com.yoyo.member.domain.Member.MemberId;
import io.jsonwebtoken.Jwt;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AuthMemberService implements AuthMemberUseCase {

    private final AuthMemberPort authMemberPort;
    private final FindMemberPort findMemberPort;
    private final UpdateMemberPort updateMemberPort;
    private final MemberMapper mapper;

    @Override
    public JwtToken loginMember(LoginMemberCommand command) {
        Long memberId = command.getMemberId();
        MemberJpaEntity memberJpaEntity = findMemberPort.findMemberById(
                new Member.MemberId(memberId)
        );
        if (memberJpaEntity.isValid()) {
            String jwtToken = authMemberPort.generateJwtToken(
                    new Member.MemberId(memberId)
            );
            String refreshToken = authMemberPort.generateRefreshToken(
                    new Member.MemberId(memberId)
            );
            updateMemberPort.updateMember(
                    new Member.MemberId(memberId),
                    new Member.MemberName(memberJpaEntity.getName()),
                    new Member.MemberPhoneNumber(memberJpaEntity.getPhoneNumber()),
                    new Member.MemberBirthDay(memberJpaEntity.getBirthDay()),
                    new Member.MemberRefreshToken(memberJpaEntity.getRefreshToken())
            );
            return JwtToken.generateJwtToken(
                    new JwtToken.MemberId(memberId),
                    new MemberJwtToken(jwtToken),
                    new MemberRefreshToken(refreshToken)
            );
        }
        return null;
    }

    @Override
    public JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command) {
        String requestRefreshToken = command.getRefreshToken();
        boolean isValid = authMemberPort.validateJwtToken(requestRefreshToken);
        if (isValid) {
            Member.MemberId memberId = authMemberPort.parseMemberIdFromToken(requestRefreshToken);
            Long checkMemberId = memberId.getMemberId();
            MemberJpaEntity memberJpaEntity = findMemberPort.findMemberById(memberId);
            if (!memberJpaEntity.getRefreshToken().equals(
                    command.getRefreshToken()
            )) {
                return null;
            }
            // refresh 정보와 요청 받은 refresh token 정보가 일치하는지 확인 된 후
            if (memberJpaEntity.isValid()) {
                String newJwtToken = authMemberPort.generateJwtToken(
                        new Member.MemberId(checkMemberId)
                );

                return JwtToken.generateJwtToken(
                        new JwtToken.MemberId(checkMemberId),
                        new JwtToken.MemberJwtToken(newJwtToken),
                        new JwtToken.MemberRefreshToken(requestRefreshToken)
                );
            }
        }
        return null;
    }

    @Override
    public boolean validateJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        return authMemberPort.validateJwtToken(jwtToken);
    }

    @Override
    public Member getMemberByJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        boolean isValid = authMemberPort.validateJwtToken(jwtToken);
        MemberJpaEntity memberJpaEntity = null;
        if (isValid) {
            Member.MemberId memberId = authMemberPort.parseMemberIdFromToken(jwtToken);
            Long checkMemberId = memberId.getMemberId();

            memberJpaEntity = findMemberPort.findMemberById(checkMemberId);
            if (!memberJpaEntity.getRefreshToken().equals(command.getJwtToken())) {
                return null;
            }
            return mapper.mapToDomainEntity(memberJpaEntity);
        }
        return null;
    }
}
