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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String name;

    private String phoneNumber;

    private String password;

    private LocalDate birthDay;

    public MemberJpaEntity(String naveValue, String passwordValue, String phoneNumberValue, LocalDate birthDayValue) {
        this.name = naveValue;
        this.password = passwordValue;
        this.phoneNumber = phoneNumberValue;
        this.birthDay = birthDayValue;
    }

}
