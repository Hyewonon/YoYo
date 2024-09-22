package com.yoyo.event.domain.event.consumer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSummaryResponse {
    private Long memberId;
    private Long eventId;
    private int transactionCount;
    private long totalAmount;
}
