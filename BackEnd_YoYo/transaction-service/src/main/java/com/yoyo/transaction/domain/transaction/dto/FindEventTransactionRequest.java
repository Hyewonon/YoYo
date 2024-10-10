package com.yoyo.transaction.domain.transaction.dto;

import lombok.Getter;

@Getter
public class FindEventTransactionRequest {
    private Long eventId;
    private String search;
    private String RelationType;
    private boolean isRegister;
}
