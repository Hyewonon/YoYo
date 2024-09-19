package com.yoyo.member.adapter.out.persistence;

import com.yoyo.common.annotation.PersistenceAdapter;
import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.member.application.port.out.FindMemberPort;
import com.yoyo.member.application.port.out.RegisterMemberPort;
import com.yoyo.member.application.port.out.UpdateMemberPort;
import com.yoyo.member.domain.Member;
import com.yoyo.member.domain.Member.MemberBirthDay;
import com.yoyo.member.domain.Member.MemberId;
import com.yoyo.member.domain.Member.MemberIsValid;
import com.yoyo.member.domain.Member.MemberName;
import com.yoyo.member.domain.Member.MemberPassword;
import com.yoyo.member.domain.Member.MemberPhoneNumber;
import com.yoyo.member.domain.Member.MemberRefreshToken;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements RegisterMemberPort, FindMemberPort, UpdateMemberPort {

    private final SpringDataMemberRepository memberRepository;

    @Override
    public MemberJpaEntity createMember(MemberName memberName, MemberPhoneNumber memberPhoneNumber,
                                        MemberPassword memberPassword,
                                        MemberBirthDay memberBirthDay,
                                        MemberIsValid memberIsValid) {
        return memberRepository.save(new MemberJpaEntity(memberName.getNameValue(),
                                                         memberPhoneNumber.getPhoneNumberValue(),
                                                         memberPassword.getPasswordValue(),
                                                         memberBirthDay.getBirthDayValue(), memberIsValid.isValid(),
                                                         ""));
    }

    @Override
    public MemberJpaEntity findMemberById(MemberId memberId) {
        return memberRepository.findById(memberId.getMemberId())
                               .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public MemberJpaEntity findMemberByPhoneNumber(MemberPhoneNumber memberPhoneNumber) {
        return memberRepository.findByPhoneNumber(memberPhoneNumber.getPhoneNumberValue());
    }


    @Override
    public MemberJpaEntity updateMember(MemberId memberId, MemberName memberName,
                                        MemberPhoneNumber memberPhoneNumber, MemberBirthDay memberBirthDay,
                                        MemberRefreshToken memberRefreshToken) {
        MemberJpaEntity memberJpaEntity = memberRepository.findById(memberId.getMemberId())
                                                          .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        memberJpaEntity.setName(memberName.getNameValue());
        memberJpaEntity.setBirthDay(memberBirthDay.getBirthDayValue());
        memberJpaEntity.setPhoneNumber(memberPhoneNumber.getPhoneNumberValue());
        memberJpaEntity.setRefreshToken(memberRefreshToken.getRefreshToken());
        return memberRepository.save(memberJpaEntity);
    }
}
