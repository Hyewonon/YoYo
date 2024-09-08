package com.yoyo.member.adapter.in.web.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginMemberRequest {

    private String phoneNumber;
    private String password;
}
