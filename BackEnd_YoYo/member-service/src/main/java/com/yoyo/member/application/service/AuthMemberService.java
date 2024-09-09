package com.yoyo.member.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.member.adapter.out.persistence.MemberJpaEntity;
import com.yoyo.member.adapter.out.persistence.MemberMapper;
import com.yoyo.member.application.port.in.auth.AuthMemberUseCase;
import com.yoyo.member.application.port.in.auth.LoginMemberCommand;
import com.yoyo.member.application.port.in.auth.LogoutMemberCommand;
import com.yoyo.member.application.port.in.auth.RefreshTokenCommand;
import com.yoyo.member.application.port.in.auth.ValidateTokenCommand;
import com.yoyo.member.application.port.out.AuthMemberPort;
import com.yoyo.member.application.port.out.FindMemberPort;
import com.yoyo.member.application.port.out.UpdateMemberPort;
import com.yoyo.member.domain.JwtToken;
import com.yoyo.member.domain.JwtToken.MemberJwtToken;
import com.yoyo.member.domain.JwtToken.MemberRefreshToken;
import com.yoyo.member.domain.Member;
import com.yoyo.member.domain.Member.MemberPhoneNumber;
import jakarta.transaction.Transactional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.redis.core.RedisTemplate;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AuthMemberService implements AuthMemberUseCase {

    private final AuthMemberPort authMemberPort;
    private final FindMemberPort findMemberPort;
    private final UpdateMemberPort updateMemberPort;
    private final MemberMapper mapper;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String TOKEN_BLACKLIST_PREFIX = "blacklist: ";

    @Override
    public JwtToken loginMember(LoginMemberCommand command) {
        String phoneNumber = command.getPhoneNumber();
        MemberJpaEntity memberJpaEntity = findMemberPort.findMemberByPhoneNumber(
                new MemberPhoneNumber(phoneNumber)
        );
        if (!BCrypt.checkpw(command.getPassword(), memberJpaEntity.getPassword())) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }
        String jwtToken = authMemberPort.generateJwtToken(
                new Member.MemberPhoneNumber(phoneNumber)
        );
        String refreshToken = authMemberPort.generateRefreshToken(
                new Member.MemberPhoneNumber(phoneNumber)
        );
        updateMemberPort.updateMember(
                new Member.MemberId(memberJpaEntity.getMemberId()),
                new Member.MemberName(memberJpaEntity.getName()),
                new Member.MemberPhoneNumber(phoneNumber),
                new Member.MemberBirthDay(memberJpaEntity.getBirthDay()),
                new Member.MemberRefreshToken(refreshToken)
        );
        return JwtToken.generateJwtToken(
                new JwtToken.MemberPhoneNumber(phoneNumber),
                new MemberJwtToken(jwtToken),
                new MemberRefreshToken(refreshToken)
        );
    }

    @Override
    public JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command) {
        String requestRefreshToken = command.getRefreshToken();
        boolean validateJwtToken = authMemberPort.validateJwtToken(requestRefreshToken);
        if (validateJwtToken) {
            Member.MemberPhoneNumber memberPhoneNumber = authMemberPort.parseMemberIdFromToken(requestRefreshToken);
            MemberJpaEntity memberJpaEntity = findMemberPort.findMemberByPhoneNumber(memberPhoneNumber);
            if (!memberJpaEntity.getRefreshToken().equals(
                    command.getRefreshToken()
            )) {
                return null;
            }
            // refresh 정보와 요청 받은 refresh token 정보가 일치하는지 확인 된 후
                String newJwtToken = authMemberPort.generateJwtToken(
                        new Member.MemberPhoneNumber(memberPhoneNumber.getPhoneNumberValue())
                );

                return JwtToken.generateJwtToken(
                        new JwtToken.MemberPhoneNumber(memberPhoneNumber.getPhoneNumberValue()),
                        new JwtToken.MemberJwtToken(newJwtToken),
                        new JwtToken.MemberRefreshToken(requestRefreshToken)
                );
        }
        return null;
    }

    @Override
    public boolean validateJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        boolean isBlackList = Boolean.TRUE.equals(redisTemplate.hasKey(TOKEN_BLACKLIST_PREFIX + jwtToken));
        if (isBlackList) {
            return false;
        }
        return authMemberPort.validateJwtToken(jwtToken);
    }

    @Override
    public Member getMemberByJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        boolean validateJwtToken = authMemberPort.validateJwtToken(jwtToken);
        MemberJpaEntity memberJpaEntity = null;
        if (validateJwtToken) {
            Member.MemberPhoneNumber memberPhoneNumber = authMemberPort.parseMemberIdFromToken(jwtToken);

            memberJpaEntity = findMemberPort.findMemberByPhoneNumber(memberPhoneNumber);
            if (!memberJpaEntity.getRefreshToken().equals(command.getJwtToken())) {
                return null;
            }
            return mapper.mapToDomainEntity(memberJpaEntity);
        }
        return null;
    }

    @Override
    public void logout(LogoutMemberCommand command) {
        String jwtToken = command.getJwtToken();
        long expiration = authMemberPort.getExpirationTime(jwtToken);
        redisTemplate.opsForValue().set(TOKEN_BLACKLIST_PREFIX + jwtToken, "logged-out", expiration,
                                        TimeUnit.MILLISECONDS);
    }
}
