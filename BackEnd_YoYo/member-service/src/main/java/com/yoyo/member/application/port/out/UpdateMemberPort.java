package com.yoyo.member.application.port.out;

import com.yoyo.member.adapter.out.persistence.entity.MemberJpaEntity;
import com.yoyo.member.domain.Member;

public interface UpdateMemberPort {

    MemberJpaEntity updateMember(
            Member.MemberId memberId,
            Member.MemberName memberName,
            Member.MemberPhoneNumber memberPhoneNumber,
            Member.MemberBirthDay memberBirthDay,
            Member.MemberRefreshToken memberRefreshToken
    );
}
