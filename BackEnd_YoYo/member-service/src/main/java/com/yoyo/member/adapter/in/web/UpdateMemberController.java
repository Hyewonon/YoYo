package com.yoyo.member.adapter.in.web;

import com.yoyo.common.annotation.WebAdapter;
import com.yoyo.member.application.port.in.UpdateMemberCommand;
import com.yoyo.member.application.port.in.UpdateMemberUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class UpdateMemberController {

    private final UpdateMemberUseCase updateMemberUseCase;

    @PatchMapping("/members/update")
    ResponseEntity<?> updateMember(@RequestBody UpdateMemberRequest request) {
        UpdateMemberCommand command = UpdateMemberCommand.builder()
                                                         .memberId(request.getMemberId())
                                                         .name(request.getName())
                                                         .phoneNumber(request.getPhoneNumber())
                                                         .birthDay(request.getBirthDay())
                                                         .build();
        updateMemberUseCase.updateMember(command);
        return ResponseEntity.ok(updateMemberUseCase.updateMember(command));
    }

}
