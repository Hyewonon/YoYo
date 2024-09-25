package com.yoyo.member.domain.member.controller;

import com.yoyo.common.response.ApiResponse;
import com.yoyo.member.domain.member.dto.RegisterMemberDTO;
import com.yoyo.member.domain.member.dto.UpdateMemberDTO;
import com.yoyo.member.domain.member.service.MemberService;
import com.yoyo.member.entity.Member;
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
        Member response = memberService.findMemberById(Long.parseLong(memberId));
        ApiResponse<Member> res = new ApiResponse<>(
                HttpStatus.OK.value(),
                "조회 성공",
                response
        );
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PatchMapping("update")
    ResponseEntity<?> updateMember(@RequestHeader("memberId") String memberId, @RequestBody UpdateMemberDTO.Request request) {

        Member member  = memberService.updateMember(Long.parseLong(memberId),request);
        ApiResponse<Member> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "수정 성공",
                member
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
