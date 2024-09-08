package com.yoyo.member.adapter.in.web;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberRequest {

    private Long memberId;
    private String name;
    private String phoneNumber;
    private LocalDate birthDay;
}
