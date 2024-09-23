package com.yoyo.member.adapter.out.persistence.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "member" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RelationJpaEntity> relations = new ArrayList<>();

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
