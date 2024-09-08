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
    private final boolean isValid;
    private final String refreshToken;
    public static Member generateMember(
            MemberId memberId, MemberName memberName,MemberPhoneNumber memberPhoneNumber, MemberPassword memberPassword,
             MemberBirthDay memberBirthDay,
            MemberIsValid memberIsValid,
            MemberRefreshToken memberRefreshToken
    ) {
        return new Member(memberId.memberId,
                          memberName.nameValue,
                          memberPhoneNumber.phoneNumberValue,
                          memberPassword.passwordValue,
                          memberBirthDay.birthDayValue,
                          memberIsValid.isValid,
                          memberRefreshToken.refreshToken);
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

    @Value
    public static class MemberRefreshToken{

        String refreshToken;

        public MemberRefreshToken(String value) {
            this.refreshToken = value;
        }
    }

    @Value
    public static class MemberIsValid{

        boolean isValid;

        public MemberIsValid(boolean value) {
            this.isValid = value;
        }
    }

    public void updateName(MemberName newValue) {
        this.name = newValue.getNameValue();
    }
}

