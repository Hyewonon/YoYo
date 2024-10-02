package com.yoyo.member.domain.member.service;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.member.domain.member.dto.RegisterMemberDTO;
import com.yoyo.member.domain.member.dto.UpdateMemberDTO;
import com.yoyo.member.domain.member.producer.MemberProducer;
import com.yoyo.member.domain.member.repository.BaseMemberRepository;
import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.member.repository.NoMemberRepository;
import com.yoyo.member.entity.BaseMember;
import com.yoyo.member.entity.Member;
import com.yoyo.member.entity.NoMember;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberProducer memberProducer;
    private final NoMemberRepository noMemberRepository;
    private final BaseMemberRepository baseMemberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    public String findBaseMemberNameById(Long memberId) {
        BaseMember baseMember = baseMemberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
        return baseMember.getName();
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

        // 회원가입 시, 싸피 금융 API userkey 생성 및 저장
        memberProducer.sendBankingUserkey(savedMember.getMemberId());

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

    /**
     * 비회원 저장
     * */
    public NoMember saveNoMember(String memberName) {
        return noMemberRepository.save(NoMember.builder()
                                        .name(memberName)
                                        .build());
    }
}
