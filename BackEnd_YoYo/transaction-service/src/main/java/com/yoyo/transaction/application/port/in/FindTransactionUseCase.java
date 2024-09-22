package com.yoyo.transaction.application.port.in;

import com.yoyo.transaction.domain.Transaction;

import java.util.List;

public interface FindTransactionUseCase {
    Transaction findTransaction(FindTransactionCommand findTransactionCommand);

    List<Transaction> findTransactionBySenderId(Long memberId);
    List<Transaction> findTransactionByReceiverId(Long memberId);
}