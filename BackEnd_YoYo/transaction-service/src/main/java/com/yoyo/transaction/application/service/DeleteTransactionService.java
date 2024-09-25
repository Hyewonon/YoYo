package com.yoyo.transaction.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.transaction.application.port.in.DeleteTransactionUseCase;
import com.yoyo.transaction.application.port.out.DeleteTransactionPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class DeleteTransactionService implements DeleteTransactionUseCase {
    private final DeleteTransactionPort deleteTransactionPort;


    @Override
    public void deleteTransaction(Long transactionId) {
        deleteTransactionPort.deleteTransaction(transactionId);
    }
}
