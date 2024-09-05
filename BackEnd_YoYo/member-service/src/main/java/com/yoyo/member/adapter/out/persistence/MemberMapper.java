package com.yoyo.member.adapter.out.persistence;

import com.yoyo.member.domain.Member;
import com.yoyo.member.domain.Member.MemberPassword;
import com.yoyo.member.domain.Member.MemberPhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member mapToDomainEntity(MemberJpaEntity memberJpaEntity) {
        return Member.generateMember(
                new Member.MemberId(memberJpaEntity.getMemberId()),
                new Member.MemberName(memberJpaEntity.getName()),
                new Member.MemberPassword(memberJpaEntity.getPassword()),
                new Member.MemberPhoneNumber(memberJpaEntity.getPhoneNumber()),
                new Member.MemberBirthDay(memberJpaEntity.getBirthDay())
        );
    }

    public MemberJpaEntity mapToJpaEntity(Member member) {
        return MemberJpaEntity.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .password(member.getPassword())
                .password(member.getPhoneNumber())
                .birthDay(member.getBirthDay())
                              .build();
    }
}
