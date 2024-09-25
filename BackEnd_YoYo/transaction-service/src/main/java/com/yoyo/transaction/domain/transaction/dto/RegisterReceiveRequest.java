package com.yoyo.transaction.domain.transaction.dto;

import lombok.Getter;

@Getter
public class RegisterReceiveRequest {
    // 받았어요 -> 내가 받은 사람
    private Long senderId;
    private String senderName;
    private Long relationId;
    private String eventName;
    private long amount;
    private String memo;
}
