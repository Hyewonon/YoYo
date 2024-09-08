package com.yoyo.member.domain;

import com.yoyo.member.domain.Member.MemberId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtToken {

    private final String memberPhoneNumber;
    private final String jwtToken;
    private final String refreshToken;
    public static JwtToken generateJwtToken(MemberPhoneNumber memberPhoneNumber,
                                            MemberJwtToken memberJwtToken,
                                            MemberRefreshToken memberRefreshToken) {
        return new JwtToken(memberPhoneNumber.getMemberPhoneNumber(),
                            memberJwtToken.jwtToken,
                            memberRefreshToken.refreshToken);
    }

    @Value
    public static class MemberPhoneNumber {

        String memberPhoneNumber;
        public MemberPhoneNumber(String value) {
            this.memberPhoneNumber = value;
        }
    }

    @Value
    public static class MemberJwtToken{
        String jwtToken;
        public MemberJwtToken(String value) {
            this.jwtToken = value;
        }
    }

    @Value
    public static class MemberRefreshToken{

        String refreshToken;

        public MemberRefreshToken(String value) {
            this.refreshToken = value;
        }
    }
}
