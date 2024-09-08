package com.yoyo.member.application.port.out;

import com.yoyo.member.domain.Member;

public interface AuthMemberPort {

    // memberId 기준으로 Jwt Token 생성
    String generateJwtToken(Member.MemberId memberId);

    // memberId 기준으로 Refresh Token 생성
    String generateRefreshToken(Member.MemberId memberId);

    // JwtToken 유효한지 확인
    boolean validateJwtToken(String jwtToken);

    // Jwt Token 어떤 memberId를 가지는지 확인
    Member.MemberId parseMemberIdFromToken(String jwtToken);

}
