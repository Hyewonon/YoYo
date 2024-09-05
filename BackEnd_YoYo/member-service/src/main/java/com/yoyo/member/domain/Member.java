package com.yoyo.member.domain;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    private final Long memberId;
    private String name;
    private final String phoneNumber;
    private final String password;
    private final LocalDate birthDay;

    public static Member generateMember(
            MemberId memberId, MemberName memberName, MemberPassword memberPassword,
            MemberPhoneNumber memberPhoneNumber, MemberBirthDay memberBirthDay
    ) {
        return new Member(memberId.memberId,
                          memberName.nameValue,
                          memberPassword.passwordValue,
                          memberPhoneNumber.phoneNumberValue,
                          memberBirthDay.birthDayValue);
    }

    @Value
    public static class MemberId {
        Long memberId;
        public MemberId(Long value) {
            this.memberId = value;
        }
    }

    @Value
    public static class MemberName {

        String nameValue;

        public MemberName(String Value) {
            this.nameValue = Value;
        }

    }

    @Value
    public static class MemberPhoneNumber {

        String phoneNumberValue;

        public MemberPhoneNumber(String value) {
            this.phoneNumberValue = value;
        }

    }

    @Value
    public static class MemberPassword {

        String passwordValue;

        public MemberPassword(String value) {
            this.passwordValue = value;
        }

    }

    @Value
    public static class MemberBirthDay {

        LocalDate birthDayValue;

        public MemberBirthDay(LocalDate birthDayValue) {
            this.birthDayValue = birthDayValue;
        }

    }

    public void updateName(MemberName newValue) {
        this.name = newValue.getNameValue();
    }
}

// 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