package com.yoyo.member.adapter.out;

import com.yoyo.member.adapter.out.persistence.entity.RelationType;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class RelationResponse {
    private Long relationId;
    private Long oppositeId;
    private String oppositeName;
    private RelationType relationType;
    private String description;
    private long totalReceivedAmount;
    private long totalSentAmount;
}
