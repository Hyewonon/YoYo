package com.yoyo.member.adapter.in.web.member;

import java.time.LocalDate;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterMemberRequest {

    private String name;
    private String phoneNumber;
    private String password;
    private LocalDate birthDay;
    private boolean isValid;
}
