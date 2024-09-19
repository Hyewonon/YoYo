package com.yoyo.member.adapter.out.persistence;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Setter
    private String name;
    @Setter
    private String phoneNumber;
    @Setter
    private String password;
    @Setter
    private LocalDate birthDay;
    @Setter
    private boolean isValid;
    @Setter
    private String refreshToken;
    @Setter
    private String accountNumber;
    @Setter
    private String bankCode;

    public MemberJpaEntity(String naveValue, String phoneNumberValue, String passwordValue, LocalDate birthDayValue,
                           boolean isValid, String refreshToken) {
        this.name = naveValue;
        this.phoneNumber = phoneNumberValue;
        this.password = passwordValue;
        this.birthDay = birthDayValue;
        this.isValid = isValid;
        this.refreshToken = refreshToken;
    }
}
