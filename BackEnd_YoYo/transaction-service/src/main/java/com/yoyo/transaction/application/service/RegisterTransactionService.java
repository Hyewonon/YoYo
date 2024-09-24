package com.yoyo.transaction.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.transaction.adapter.out.persistence.TransactionJpaEntity;
import com.yoyo.transaction.adapter.out.persistence.TransactionMapper;
import com.yoyo.transaction.application.port.in.RegisterTransactionCommand;
import com.yoyo.transaction.application.port.in.RegisterTransactionUseCase;
import com.yoyo.transaction.application.port.out.RegisterTransactionPort;
import com.yoyo.transaction.domain.Transaction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@UseCase
public class RegisterTransactionService implements RegisterTransactionUseCase {

    private final RegisterTransactionPort registerTransactionPort;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction registerTransaction(RegisterTransactionCommand command) {
        TransactionJpaEntity transactionJpaEntity = registerTransactionPort.createTransaction(
                new Transaction.TransactionSenderId(command.getSenderId()),
                new Transaction.TransactionSenderName(command.getSenderName()),
                new Transaction.TransactionReceiverId(command.getReceiverId()),
                new Transaction.TransactionReceiverName(command.getReceiverName()),
                new Transaction.TransactionEventId(command.getEventId()),
                new Transaction.TransactionTitle(command.getEventName()),
                new Transaction.TransactionIsMember(false),
                new Transaction.TransactionAmount(command.getAmount()),
                new Transaction.TransactionMemo(command.getMemo()),
                command.getTransactionType()
        );
        return transactionMapper.mapToDomainEntity(transactionJpaEntity);
    }
}
