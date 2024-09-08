package com.yoyo.member.application.port.in.member;

import com.yoyo.common.annotation.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindMemberCommand extends SelfValidating<FindMemberCommand> {

    private final Long memberId;
}
