package com.yoyo.member.domain.member.controller;

import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.response.ApiResponse;
import com.yoyo.member.domain.member.dto.FCMTokenDTO;
import com.yoyo.member.domain.member.dto.RegisterMemberDTO;
import com.yoyo.member.domain.member.dto.UpdateMemberDTO;
import com.yoyo.member.domain.member.service.MemberService;
import com.yoyo.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/yoyo/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@RequestBody RegisterMemberDTO.Request request) {
        RegisterMemberDTO.Response response = memberService.registerMember(request);
        ApiResponse<RegisterMemberDTO.Response> res = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "회원가입 성공",
                response
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("")
    public ResponseEntity<?> getMember(@RequestHeader("memberId") String memberId) {
        ApiResponse<Member> res;
        Member response = memberService.findMemberById(Long.parseLong(memberId));
        if (response == null) {
            res = new ApiResponse<>(
                    HttpStatus.NO_CONTENT.value(),
                    "조회 실패",
                    null
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(res);
        }
        res = new ApiResponse<>(
                HttpStatus.OK.value(),
                "조회 성공",
                response
        );
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PatchMapping("/update")
    ResponseEntity<?> updateMember(@RequestHeader("memberId") String memberId, @RequestBody UpdateMemberDTO.Request request) {

        Member member = memberService.updateMember(Long.parseLong(memberId), request);
        ApiResponse<Member> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "수정 성공",
                member
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * fcm 토큰 저장
     * **/
    @PostMapping("/fcm-token")
    @Operation(summary = "FCM Token 저장", description = "FCM Token을 저장한다")
    public ResponseEntity<?> saveFcmToken(@RequestHeader("memberId") String memberId,
            @RequestBody @Parameter(description = "FCM 토큰") FCMTokenDTO fcmToken) {
        memberService.saveFcmToken(Long.parseLong(memberId), fcmToken.getPushToken());
        ApiResponse<Member> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "FCM Token 저장",
                null
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * fcm 토큰 삭제
     * **/
    @DeleteMapping("/fcm-token")
    @Operation(summary = "FCM Token 삭제", description = "FCM Token을 삭제한다")
    public ResponseEntity<?> deleteFcmToken(@RequestHeader("memberId") String memberId){
        memberService.deleteFcmToken(Long.parseLong(memberId));
        ApiResponse<Member> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "FCM Token 삭제",
                null
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
