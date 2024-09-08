package com.yoyo.member.application.port.out;

import com.yoyo.member.domain.Member;

public interface AuthMemberPort {

    // memberPhoneNumber 기준으로 Jwt Token 생성
    String generateJwtToken(Member.MemberPhoneNumber memberPhoneNumber);

    // memberPhoneNumber 기준으로 Refresh Token 생성
    String generateRefreshToken(Member.MemberPhoneNumber memberPhoneNumber);

    // JwtToken 유효한지 확인
    boolean validateJwtToken(String jwtToken);

    // Jwt Token 어떤 memberId를 가지는지 확인
    Member.MemberPhoneNumber parseMemberIdFromToken(String jwtToken);

}
