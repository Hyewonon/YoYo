package com.yoyo.member.domain.member.service;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.member.domain.member.dto.RegisterMemberDTO;
import com.yoyo.member.domain.member.dto.UpdateMemberDTO;
import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public String findMemberNameById(Long memberId) {
        return findMemberById(memberId).getName();
    }

    public RegisterMemberDTO.Response registerMember(RegisterMemberDTO.Request request) {
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        if (!request.isValid()) {
            throw new IllegalArgumentException("전화번호 미인증");
        }
        Member member = Member.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .password(hashedPassword)
                .birthDay(request.getBirthDay())
                .isValid(request.isValid())
                .build();
        Member savedMember = memberRepository.save(member);

        return new RegisterMemberDTO.Response(
                savedMember.getMemberId(),
                savedMember.getName(),
                savedMember.getPhoneNumber(),
                savedMember.getPassword(),
                savedMember.getBirthDay(),
                savedMember.isValid(),
                ""
        );
    }


    public Member updateMember(Long memberId,UpdateMemberDTO.Request request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        member.setName(request.getName());
        member.setBirthDay(request.getBirthDay());
        member.setPhoneNumber(request.getPhoneNumber());
        return memberRepository.save(member);
    }
}
