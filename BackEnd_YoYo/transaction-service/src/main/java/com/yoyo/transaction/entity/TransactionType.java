package com.yoyo.transaction.entity;

public enum TransactionType {
    SEND,       // 직접 등록 보낸 사람일 시
    RECEIVE,    // 직접 등록 받은 사람일 시
    AUTO        // 자동 등록
    ;
}
