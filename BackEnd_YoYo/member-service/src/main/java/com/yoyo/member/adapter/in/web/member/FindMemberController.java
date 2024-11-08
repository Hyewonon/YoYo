package com.yoyo.member.adapter.in.web.member;

import com.yoyo.member.application.port.in.member.FindMemberCommand;
import com.yoyo.member.application.port.in.member.FindMemberUseCase;
import com.yoyo.member.domain.Member;
import com.yoyo.member.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindMemberController {

    private final FindMemberUseCase findMemberUseCase;

    @GetMapping("/yoyo/members/{memberId}")
    ResponseEntity<?> findMember(@PathVariable("memberId") Long memberId) {
        FindMemberCommand command = FindMemberCommand.builder()
                                                     .memberId(memberId)
                                                     .build();
        Member member =  findMemberUseCase.findMember(command);
        ApiResponse<Member> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "멤버 조회",
                member
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
