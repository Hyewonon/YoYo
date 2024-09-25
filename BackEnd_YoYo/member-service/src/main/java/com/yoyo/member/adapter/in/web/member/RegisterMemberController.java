package com.yoyo.member.adapter.in.web.member;

import com.yoyo.common.annotation.WebAdapter;
import com.yoyo.member.application.port.in.member.RegisterMemberCommand;
import com.yoyo.member.application.port.in.member.RegisterMemberUseCase;
import com.yoyo.member.domain.Member;
import com.yoyo.member.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMemberController {

    private final RegisterMemberUseCase registerMemberUseCase;

    @PostMapping("/yoyo/members/register")
    ResponseEntity<?>registerMember(@RequestBody RegisterMemberRequest request) {
        // request -> command
        RegisterMemberCommand command = RegisterMemberCommand.builder()
                                                             .name(request.getName())
                                                             .phoneNumber(request.getPhoneNumber())
                                                             .password(request.getPassword())
                                                             .birthDay(request.getBirthDay())
                                                             .isValid(request.isValid())
                                                             .build();
        // command -> Usecase
        ApiResponse<Member> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "등록 성공",
                registerMemberUseCase.registerMember(command)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
