package com.yoyo.member.adapter.in.web.member;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberRequest {

    private String name;
    private String phoneNumber;
    private LocalDate birthDay;
}
