package com.yoyo.member.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.member.adapter.out.persistence.SpringDataMemberRepository;
import com.yoyo.member.application.port.in.ValidateMemberUseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ValidateMemberService implements ValidateMemberUseCase {

    private final SpringDataMemberRepository memberRepository;
    @Override
    public boolean validateMember(Long memberId) {
        return memberRepository.findById(memberId).isPresent();
    }
}
