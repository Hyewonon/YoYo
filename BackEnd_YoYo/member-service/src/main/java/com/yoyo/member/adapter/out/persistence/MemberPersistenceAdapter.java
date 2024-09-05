package com.yoyo.member.adapter.out.persistence;

import com.yoyo.common.annotation.PersistenceAdapter;
import com.yoyo.member.application.port.out.FindMemberPort;
import com.yoyo.member.application.port.out.RegisterMemberPort;
import com.yoyo.member.application.port.out.UpdateMemberPort;
import com.yoyo.member.domain.Member;
import com.yoyo.member.domain.Member.MemberBirthDay;
import com.yoyo.member.domain.Member.MemberId;
import com.yoyo.member.domain.Member.MemberName;
import com.yoyo.member.domain.Member.MemberPassword;
import com.yoyo.member.domain.Member.MemberPhoneNumber;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements RegisterMemberPort, FindMemberPort, UpdateMemberPort {

    private final SpringDataMemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public MemberJpaEntity createMember(MemberName memberName, MemberPassword memberPassword,
                                        MemberPhoneNumber memberPhoneNumber,
                                        MemberBirthDay memberBirthDay) {
        return memberRepository.save(new MemberJpaEntity(memberName.getNameValue(),
                                                         memberPassword.getPasswordValue(),
                                                         memberPhoneNumber.getPhoneNumberValue(),
                                                         memberBirthDay.getBirthDayValue()));
    }

    @Override
    public Member findMember(MemberId memberId) {
        return memberMapper.mapToDomainEntity(memberRepository.findById(memberId.getMemberId())
                               .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId.getMemberId())));
    }

    @Override
    public Member findMemberById(Long memberId) {
        return memberMapper.mapToDomainEntity(memberRepository.findById(memberId)
                                                      .orElseThrow(() -> new EntityNotFoundException("Not Fount MemberId: " + memberId)));
    }

    @Override
    public void save(Member member) {
        memberRepository.save(memberMapper.mapToJpaEntity(member));
    }
}
