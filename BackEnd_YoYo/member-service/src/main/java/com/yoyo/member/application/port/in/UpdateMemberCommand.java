package com.yoyo.member.application.port.in;

import com.yoyo.common.annotation.SelfValidating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberCommand extends SelfValidating<UpdateMemberCommand> {

    private Long memberId;
    private String name;
}
