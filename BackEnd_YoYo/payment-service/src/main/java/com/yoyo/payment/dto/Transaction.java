package com.yoyo.payment.dto;

import lombok.Data;

@Data
public class Transaction {
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Long eventId;
    private String title;
    private boolean isMember;
    private int amount;
    private String memo;
}
