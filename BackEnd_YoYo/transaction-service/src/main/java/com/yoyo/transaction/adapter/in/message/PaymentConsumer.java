package com.yoyo.transaction.adapter.in.message;

import com.yoyo.transaction.adapter.in.web.CreateNonMemberTransactionRequest;
import com.yoyo.transaction.adapter.out.persistence.SpringDataTransactionRepository;
import com.yoyo.transaction.adapter.out.persistence.TransactionJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {
    private final SpringDataTransactionRepository transactionRepository;

    @KafkaListener(topics = "payment-success", groupId = "transaction-service")
    public void setTransaction(CreateNonMemberTransactionRequest request) {
        TransactionJpaEntity jpaEntity = new TransactionJpaEntity(
                request.getSenderName(),
                request.getReceiverId(),
                request.getReceiverName(),
                request.getEventId(),
                request.getTitle(),
                request.getAmount(),
                request.getMemo()
        );
        transactionRepository.save(jpaEntity);
    }
}


