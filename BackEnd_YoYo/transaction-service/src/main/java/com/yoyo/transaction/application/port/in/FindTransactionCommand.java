package com.yoyo.transaction.application.port.in;

import com.yoyo.common.annotation.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindTransactionCommand extends SelfValidating<FindTransactionCommand> {
    private Long transactionId;
}
