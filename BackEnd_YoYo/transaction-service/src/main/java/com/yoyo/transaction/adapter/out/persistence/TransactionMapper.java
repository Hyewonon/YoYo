package com.yoyo.transaction.adapter.out.persistence;

import com.yoyo.transaction.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction mapToDomainEntity(TransactionJpaEntity transactionJpaEntity) {
        return Transaction.generateTransaction(
                new Transaction.TransactionId(transactionJpaEntity.getTransactionId()),
                new Transaction.TransactionSenderId(transactionJpaEntity.getSenderId()),
                new Transaction.TransactionSenderName(transactionJpaEntity.getSenderName()),
                new Transaction.TransactionReceiverId(transactionJpaEntity.getReceiverId()),
                new Transaction.TransactionReceiverName(transactionJpaEntity.getReceiverName()),
                new Transaction.TransactionEventId(transactionJpaEntity.getEventId()),
                new Transaction.TransactionTitle(transactionJpaEntity.getTitle()),
                new Transaction.TransactionIsMember(transactionJpaEntity.isMember()),
                new Transaction.TransactionAmount(transactionJpaEntity.getAmount()),
                new Transaction.TransactionMemo(transactionJpaEntity.getMemo()),
                transactionJpaEntity.getTransactionType()
        );
    }

    public TransactionJpaEntity mapToJpaEntity(Transaction transaction) {
        return TransactionJpaEntity.builder()
                .transactionId(transaction.getTransactionId())
                .senderId(transaction.getSenderId())
                .senderName(transaction.getSenderName())
                .receiverId(transaction.getReceiverId())
                .receiverName(transaction.getReceiverName())
                .eventId(transaction.getEventId())
                .title(transaction.getTitle())
                .isMember(transaction.isMember())
                .amount(transaction.getAmount())
                .memo(transaction.getMemo())
                .transactionType(transaction.getTransactionType())
                .build();
    }

}
