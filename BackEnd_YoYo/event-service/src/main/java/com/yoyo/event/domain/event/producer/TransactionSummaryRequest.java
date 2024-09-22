package com.yoyo.event.domain.event.producer;

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
