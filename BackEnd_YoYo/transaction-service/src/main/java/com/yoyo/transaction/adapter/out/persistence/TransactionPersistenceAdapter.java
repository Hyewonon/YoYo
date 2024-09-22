package com.yoyo.transaction.adapter.out.persistence;

import com.yoyo.common.annotation.PersistenceAdapter;
import com.yoyo.transaction.application.port.out.FindTransactionPort;
import com.yoyo.transaction.application.port.out.RegisterTransactionPort;
import com.yoyo.transaction.domain.Transaction;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.internals.Sender;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements RegisterTransactionPort, FindTransactionPort {
    private final TransactionMapper transactionMapper;
    private final SpringDataTransactionRepository transactionRepository;

    @Override
    public Transaction findTransaction(Transaction.TransactionId transactionId) {
        return transactionMapper.mapToDomainEntity(transactionRepository.findById(transactionId.getTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("Transaction Not Fount" + transactionId.getTransactionId())));
    }

    @Override
    public List<Transaction> findTransactionsBySenderId(Long memberId) {
        return transactionRepository.findAllBySenderId(memberId).stream()
                .map(transactionMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findTransactionsByReceiverId(Long memberId) {
        return transactionRepository.findAllByReceiverId(memberId).stream()
                .map(transactionMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionJpaEntity createTransaction(Transaction.TransactionSenderId transactionSenderId, Transaction.TransactionSenderName senderName, Transaction.TransactionReceiverId receiverId, Transaction.TransactionReceiverName transactionReceiverName, Transaction.TransactionEventId transactionEventId, Transaction.TransactionTitle transactionTitle, Transaction.TransactionIsMember transactionIsMember, Transaction.TransactionAmount transactionAmount, Transaction.TransactionMemo transactionMemo, TransactionType transactionType) {
        return transactionRepository.save(new TransactionJpaEntity(
                transactionSenderId.getMemberId(),
                senderName.getSenderName(),
                receiverId.getMemberId(),
                transactionReceiverName.getReceiverName(),
                transactionEventId.getEventId(),
                transactionTitle.getTitle(),
                transactionIsMember.isMember(),
                transactionAmount.getAmount(),
                transactionMemo.getMemo(),
                transactionType
        ));
    }
}
