package com.yoyo.member.adapter.in.web;

import com.yoyo.common.WebAdapter;
import com.yoyo.member.application.port.in.RegisterMemberCommand;
import com.yoyo.member.application.port.in.RegisterMemberUseCase;
import com.yoyo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController("")
@RequiredArgsConstructor
public class RegisterMemberController {

    private final RegisterMemberUseCase registerMemberUseCase;

    @PostMapping("/members/register")
    Member registerMember(@RequestBody RegisterMemberRequest request) {
        // request

        // request -> command

        // command -> Usecase

        RegisterMemberCommand command = RegisterMemberCommand.builder()
                                                             .name(request.getName())
                                                             .password(request.getPassword())
                                                             .birthDay(request.getBirthDay())
                                                             .build();
        return registerMemberUseCase.registerMember(command);
    }
}
