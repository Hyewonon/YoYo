package com.yoyo.member.application.port.out;

import com.yoyo.member.domain.Member;

public interface AuthMemberPort {

    String generateJwtToken(Member.MemberId memberId);

    String generateRefreshToken(Member.MemberId memberId);

    // JwtToken 유효한지 확인
    boolean validateJwtToken(String jwtToken);

    Member.MemberId parseMemberIdFromToken(String jwtToken);

    long getExpirationTime(String jwtToken);
}
