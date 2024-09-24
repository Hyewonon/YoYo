package com.yoyo.transaction.application.port.in;

import com.yoyo.transaction.domain.Transaction;

public interface RegisterTransactionUseCase {

    Transaction registerTransaction(RegisterTransactionCommand command);
}
