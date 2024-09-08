package com.yoyo.member.adapter.in.web.auth;

import com.yoyo.common.annotation.WebAdapter;
import com.yoyo.member.application.port.in.auth.AuthMemberUseCase;
import com.yoyo.member.application.port.in.auth.LoginMemberCommand;
import com.yoyo.member.application.port.in.auth.RefreshTokenCommand;
import com.yoyo.member.application.port.in.auth.ValidateTokenCommand;
import com.yoyo.member.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class AuthMemberController {

    private final AuthMemberUseCase authMemberUseCase;

    @PostMapping("/members/login")
    JwtToken loginMember(@RequestBody LoginMemberRequest request) {
        LoginMemberCommand command = LoginMemberCommand.builder()
                                                       .phoneNumber(request.getPhoneNumber())
                                                       .password(request.getPassword())
                                                       .build();
        return authMemberUseCase.loginMember(command);
    }

    @PostMapping("/members/refresh-token")
    JwtToken refreshToken(@RequestBody RefreshTokenRequest request) {
        RefreshTokenCommand command = RefreshTokenCommand.builder()
                                                         .refreshToken(request.getRefreshToken())
                                                         .build();
        return authMemberUseCase.refreshJwtTokenByRefreshToken(command);
    }

    @PostMapping("/members/token-validate")
    boolean validateToken(@RequestBody ValidateTokenRequest request) {
        ValidateTokenCommand command = ValidateTokenCommand.builder()
                                                           .jwtToken(request.getJwtToken())
                                                           .build();
        return authMemberUseCase.validateJwtToken(command);
    }
}
