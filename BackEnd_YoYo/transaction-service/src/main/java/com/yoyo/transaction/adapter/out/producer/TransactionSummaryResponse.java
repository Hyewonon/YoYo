package com.yoyo.transaction.adapter.out.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionSummaryResponse {
    private Long receiverId;
    private Long eventId;
    private int senderCount;
    private long totalAmount;
}
