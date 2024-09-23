package com.yoyo.member.adapter.in.web.member;

import com.yoyo.common.annotation.WebAdapter;
import com.yoyo.member.application.port.in.member.UpdateMemberCommand;
import com.yoyo.member.application.port.in.member.UpdateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class UpdateMemberController {

    private final UpdateMemberUseCase updateMemberUseCase;

    @PatchMapping("/yoyo/members/update")
    ResponseEntity<?> updateMember(@RequestHeader("memberId") String memberId, @RequestBody UpdateMemberRequest request) {
        UpdateMemberCommand command = UpdateMemberCommand.builder()
                .memberId(Long.parseLong(memberId))
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .birthDay(request.getBirthDay())
                .build();
        updateMemberUseCase.updateMember(command);
        return ResponseEntity.ok(updateMemberUseCase.updateMember(command));
    }
}
