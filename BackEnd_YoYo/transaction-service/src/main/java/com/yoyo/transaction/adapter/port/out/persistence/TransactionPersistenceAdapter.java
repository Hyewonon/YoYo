package com.yoyo.transaction.adapter.port.out.persistence;

import com.yoyo.common.annotation.PersistenceAdapter;
import com.yoyo.transaction.application.port.out.FindTransactionPort;
import com.yoyo.transaction.domain.Transaction;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements FindTransactionPort {
    private final TransactionMapper transactionMapper;
    private final SpringDataTransactionRepository transactionRepository;
    @Override
    public Transaction findTransaction(Transaction.TransactionId transactionId) {
        return transactionMapper.mapToDomainEntity(transactionRepository.findById(transactionId.getTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("Transaction Not Fount" + transactionId.getTransactionId())));
    }
}
