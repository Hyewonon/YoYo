package com.yoyo.member.application.port.in.auth;

import com.yoyo.member.domain.JwtToken;
import com.yoyo.member.domain.Member;

public interface AuthMemberUseCase {

    JwtToken loginMember(LoginMemberCommand command);

    JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command);

    Member getMemberByJwtToken(ValidateTokenCommand command);

    void logout(LogoutMemberCommand command);
}
