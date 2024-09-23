package com.yoyo.member.application.port.in.relation;

import com.yoyo.common.annotation.SelfValidating;

public class FindRelationCommand extends SelfValidating<FindRelationCommand> {
    private Long memberId;
    private String tag;
    private String search;
}
