package com.yoyo.member.adapter.in.web;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterMemberRequest {

    private String name;
    private String phoneNumber;
    private String password;
    private LocalDate birthDay;
}
