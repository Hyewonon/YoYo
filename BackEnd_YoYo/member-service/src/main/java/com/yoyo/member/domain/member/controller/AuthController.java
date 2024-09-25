package com.yoyo.member.domain.member.controller;

import com.yoyo.common.response.ApiResponse;
import com.yoyo.member.domain.member.dto.LoginMemberDTO;
import com.yoyo.member.domain.member.dto.RefreshTokenDTO;
import com.yoyo.member.domain.member.service.AuthMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/yoyo/members")
@RequiredArgsConstructor
public class AuthController {

    private final AuthMemberService authMemberService;

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody LoginMemberDTO.Request request) {
        LoginMemberDTO.Response response = authMemberService.loginMember(request);
        ApiResponse<LoginMemberDTO.Response> res = new ApiResponse<>(
                HttpStatus.OK.value(),
                "로그인 성공",
                response
        );
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        RefreshTokenDTO.Response response = authMemberService.refreshJwtTokenByRefreshToken(refreshToken);
        ApiResponse<RefreshTokenDTO.Response> res = new ApiResponse<>(
                HttpStatus.OK.value(),
                "토큰 재발급",
                response
        );
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String request) {
        authMemberService.logout(request);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "로그아웃 완료",
                null
        );
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 완료");
    }
}
