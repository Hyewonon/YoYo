package com.yoyo.transaction.adapter.in.web;

import lombok.Getter;

@Getter
public class RegisterTransactionRequest {
    private  Long senderId;
    private  String senderName;
    private  Long receiverId;
    private  String receiverName;
    private  Long eventId;
    private  String title;
    private  boolean isMember;
    private  long amount;
    private  String memo;
}
