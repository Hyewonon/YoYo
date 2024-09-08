package com.yoyo.member.application.port.in;

import com.yoyo.member.domain.JwtToken;
import com.yoyo.member.domain.Member;

public interface AuthMemberUseCase {

    JwtToken loginMember(LoginMemberCommand command);

    JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command);

    boolean validateJwtToken(ValidateTokenCommand command);

    Member getMemberByJwtToken(ValidateTokenCommand command);

}
