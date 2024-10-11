package com.yoyo.transaction.application.port.out;

import com.yoyo.transaction.adapter.out.persistence.TransactionJpaEntity;
import com.yoyo.transaction.adapter.out.persistence.TransactionType;
import com.yoyo.transaction.domain.Transaction;

public interface RegisterTransactionPort {

    TransactionJpaEntity createTransaction(
            Transaction.TransactionSenderId transactionSenderId,
            Transaction.TransactionSenderName senderName,
            Transaction.TransactionReceiverId receiverId,
            Transaction.TransactionReceiverName transactionReceiverName,
            Transaction.TransactionEventId transactionEventId,
            Transaction.TransactionTitle transactionTitle,
            Transaction.TransactionIsMember transactionIsMember,
            Transaction.TransactionAmount transactionAmount,
            Transaction.TransactionMemo transactionMemo,
            TransactionType transactionType
            );
}
