package com.yoyo.member.domain.member.service;

import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.member.repository.NoMemberRepository;
import com.yoyo.member.entity.NoMember;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class NoMemberService {

    private final NoMemberRepository noMemberRepository;

    public NoMember registerNoMember(String name) {
        return noMemberRepository.save(NoMember.builder()
                                      .name(name)
                                      .build());
    }
}
