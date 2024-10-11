package com.yoyo.member.adapter.in.web.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateTokenRequest {

    private String jwtToken;
}
