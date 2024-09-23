package com.yoyo.member.adapter.in.web.auth;

import com.yoyo.common.annotation.WebAdapter;
import com.yoyo.member.application.port.in.auth.AuthMemberUseCase;
import com.yoyo.member.application.port.in.auth.LoginMemberCommand;
import com.yoyo.member.application.port.in.auth.LogoutMemberCommand;
import com.yoyo.member.application.port.in.auth.RefreshTokenCommand;
import com.yoyo.member.domain.JwtToken;
import com.yoyo.member.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class AuthMemberController {

    private final AuthMemberUseCase authMemberUseCase;

    @PostMapping("/yoyo/members/login")
    public ResponseEntity<?> loginMember(@RequestBody LoginMemberRequest request) {
        LoginMemberCommand command = LoginMemberCommand.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .build();
        JwtToken jwtToken = authMemberUseCase.loginMember(command);
        ApiResponse<JwtToken> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "로그인 성공",
                jwtToken
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/yoyo/members/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }
        RefreshTokenCommand command = RefreshTokenCommand.builder()
                .refreshToken(refreshToken).build();
        JwtToken jwtToken = authMemberUseCase.refreshJwtTokenByRefreshToken(command);
        ApiResponse<JwtToken> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "토큰 재발급",
                jwtToken
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/yoyo/members/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutMemberRequest request) {
        LogoutMemberCommand command = LogoutMemberCommand.builder()
                .jwtToken(request.getJwtToken())
                .build();
        authMemberUseCase.logout(command);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "로그아웃 완료",
                null
        );
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }
}
