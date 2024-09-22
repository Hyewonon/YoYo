package com.yoyo.member.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtToken {

    private final String memberId;
    private final String jwtToken;
    private final String refreshToken;

    public static JwtToken generateJwtToken(MemberId memberId,
                                            MemberJwtToken memberJwtToken,
                                            MemberRefreshToken memberRefreshToken) {
        return new JwtToken(memberId.getMemberId(),
                memberJwtToken.jwtToken,
                memberRefreshToken.refreshToken);
    }

    @Value
    public static class MemberId {
        String memberId;

        public MemberId(String value) {
            this.memberId = value;
        }
    }

    @Value
    public static class MemberJwtToken {
        String jwtToken;

        public MemberJwtToken(String value) {
            this.jwtToken = value;
        }
    }

    @Value
    public static class MemberRefreshToken {
        String refreshToken;

        public MemberRefreshToken(String value) {
            this.refreshToken = value;
        }
    }
}
