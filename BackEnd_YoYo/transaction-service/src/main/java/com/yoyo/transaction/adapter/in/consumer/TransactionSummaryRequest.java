package com.yoyo.transaction.adapter.in.consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionSummaryRequest {
    private Long receiverId;
    private Long eventId;
}
