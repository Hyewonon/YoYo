package com.yoyo.member.application.port.out;


import com.yoyo.member.adapter.out.persistence.MemberJpaEntity;
import com.yoyo.member.domain.Member;

public interface RegisterMemberPort {

    MemberJpaEntity createMember(
        Member.MemberName memberName,
        Member.MemberPhoneNumber memberPhoneNumber,
        Member.MemberPassword memberPassword,
        Member.MemberBirthDay memberBirthDay,
        Member.MemberIsValid memberIsValid
    );
}
