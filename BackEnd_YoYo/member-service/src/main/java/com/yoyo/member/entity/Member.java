package com.yoyo.member.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

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

    @OneToMany(mappedBy = "member" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Relation> relations = new ArrayList<>();

    public Member(String naveValue, String phoneNumberValue, String passwordValue, LocalDate birthDayValue,
                           boolean isValid, String refreshToken) {
        this.name = naveValue;
        this.phoneNumber = phoneNumberValue;
        this.password = passwordValue;
        this.birthDay = birthDayValue;
        this.isValid = isValid;
        this.refreshToken = refreshToken;
    }
}
