package com.yoyo.member.domain.member.service;

import com.yoyo.member.domain.member.dto.LoginMemberDTO;
import com.yoyo.member.domain.member.dto.RefreshTokenDTO;
import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.entity.Member;
import com.yoyo.member.global.util.JwtTokenUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthMemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String TOKEN_BLACKLIST_PREFIX = "blacklist: ";

    public LoginMemberDTO.Response loginMember(LoginMemberDTO.Request request) {
        String phoneNumber = request.getPhoneNumber();
        Member member = memberRepository.findByPhoneNumber(phoneNumber);
        if (!BCrypt.checkpw(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }
        Long memberId = member.getMemberId();
        String jwtToken = jwtUtil.generateJwtToken(memberId);
        String refreshToken = jwtUtil.generateRefreshToken(memberId);
        member.setRefreshToken(refreshToken);
        return new LoginMemberDTO.Response(memberId, jwtToken, refreshToken);
    }

    public RefreshTokenDTO.Response refreshJwtTokenByRefreshToken(String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }
        boolean validateJwtToken = jwtUtil.validateJwtToken(refreshToken);
        if (validateJwtToken) {
            Long memberId = jwtUtil.parseMemberIdFromToken(refreshToken);
            Member member = memberRepository.findByMemberId(memberId);
            if (!member.getRefreshToken().equals(refreshToken)) {
                return null;
            }
            // refresh 정보와 요청 받은 refresh token 정보가 일치하는지 확인 된 후
            String newJwtToken = jwtUtil.generateJwtToken(memberId);
            return new RefreshTokenDTO.Response(memberId, newJwtToken, refreshToken);
        }
        return null;
    }

    public void logout(String jwtToken) {
        long expiration = jwtUtil.getExpirationTime(jwtToken);
        redisTemplate.opsForValue().set(TOKEN_BLACKLIST_PREFIX + jwtToken, "logged-out", expiration,
                TimeUnit.MILLISECONDS);
    }
}
