package com.yoyo.member.application.port.out;


import com.yoyo.member.adapter.out.persistence.MemberJpaEntity;
import com.yoyo.member.domain.Member;

public interface RegisterMemberPort {

    MemberJpaEntity createMember(
        Member.MemberName memberName,
        Member.MemberPassword memberPassword,
        Member.MemberPhoneNumber memberPhoneNumber,
        Member.MemberBirthDay memberBirthDay
    );
}
