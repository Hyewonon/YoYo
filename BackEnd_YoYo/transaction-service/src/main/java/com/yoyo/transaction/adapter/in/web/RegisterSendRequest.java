package com.yoyo.transaction.adapter.in.web;

import lombok.Getter;

@Getter
public class RegisterSendRequest {
    // 보냈어요 -> 내가 보낸 사람
    private Long receiverId;
    private String receiverName;
    private Long relationId;
    private Long eventId;
    private String eventName;
    private long amount;
    private String memo;
}
