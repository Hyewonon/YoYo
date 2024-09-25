package com.yoyo.transaction.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.transaction.application.port.in.FindTransactionCommand;
import com.yoyo.transaction.application.port.in.FindTransactionUseCase;
import com.yoyo.transaction.application.port.out.FindTransactionPort;
import com.yoyo.transaction.domain.Transaction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindTransactionService implements FindTransactionUseCase {
    private final FindTransactionPort findTransactionPort;
    @Override
    public Transaction findTransaction(FindTransactionCommand findTransactionCommand) {
        return findTransactionPort.findTransaction(new Transaction.TransactionId(findTransactionCommand.getTransactionId()));
    }

    @Override
    public List<Transaction> findTransactionBySenderAndReceiver(Long senderId, Long receiverId) {
        return findTransactionPort.findBySenderAndReceiver(senderId, receiverId);
    }

}
