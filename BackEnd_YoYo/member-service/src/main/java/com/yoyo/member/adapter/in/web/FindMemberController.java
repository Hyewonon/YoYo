package com.yoyo.member.adapter.in.web;

import com.yoyo.member.application.port.in.FindMemberCommand;
import com.yoyo.member.application.port.in.FindMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FindMemberController {

    private final FindMemberUseCase findMemberUseCase;

    @GetMapping("/members/{memberId}")
    ResponseEntity<?> findMember(@PathVariable("memberId") Long memberId) {
        FindMemberCommand command = FindMemberCommand.builder()
                                                     .memberId(memberId)
                                                     .build();
        return ResponseEntity.ok(findMemberUseCase.findMember(command));
    }
}
