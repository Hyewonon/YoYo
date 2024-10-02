package com.yoyo.member.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "member")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseMember{

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
    @Setter
    private String fcmToken;

    @OneToMany(mappedBy = "member" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Relation> relations = new ArrayList<>();

    public Member(String naveValue, String phoneNumberValue, String passwordValue, LocalDate birthDayValue,
                           boolean isValid, String refreshToken) {
        this.setName(naveValue);
        this.phoneNumber = phoneNumberValue;
        this.password = passwordValue;
        this.birthDay = birthDayValue;
        this.isValid = isValid;
        this.refreshToken = refreshToken;
    }
}
