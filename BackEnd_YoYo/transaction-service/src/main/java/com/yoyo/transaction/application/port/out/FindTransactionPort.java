package com.yoyo.transaction.application.port.out;

import com.yoyo.transaction.domain.Transaction;

import java.util.List;


public interface FindTransactionPort {
    Transaction findTransaction(Transaction.TransactionId transactionId);

    List<Transaction> findBySenderAndReceiver(Long senderId, Long receiverId);
}
