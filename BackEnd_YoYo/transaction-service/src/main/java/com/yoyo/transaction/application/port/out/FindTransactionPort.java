package com.yoyo.transaction.application.port.out;

import com.yoyo.transaction.domain.Transaction;

public interface FindTransactionPort {
    Transaction findTransaction(Transaction.TransactionId transactionId);
}
