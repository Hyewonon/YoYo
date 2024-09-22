package com.yoyo.transaction.adapter.in.consumer;

import com.yoyo.transaction.adapter.out.persistence.SpringDataTransactionRepository;
import com.yoyo.transaction.adapter.out.persistence.TransactionJpaEntity;
import com.yoyo.transaction.adapter.out.producer.TransactionProducer;
import com.yoyo.transaction.adapter.out.producer.TransactionSummaryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionConsumer {
    private final SpringDataTransactionRepository transactionRepository;
    private final TransactionProducer producer;
    private final String GROUP_ID = "transaction-service";

    @KafkaListener(topics = "payment-success", groupId = GROUP_ID)
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

    @KafkaListener(topics = "transaction-topic", groupId = GROUP_ID)
    public void getEventInformation(TransactionSummaryRequest message) {
        List<TransactionJpaEntity> transactions = transactionRepository.findByReceiverIdAndEventId(message.getReceiverId(), message.getEventId());
        int transactionCount = transactions.size();
        long totalAmount = transactions.stream().mapToLong(TransactionJpaEntity::getAmount).sum();
        log.info("Event ID: {}, Receiver ID: {}, Total Transactions: {}, Total Amount: {}",
                message.getEventId(), message.getReceiverId(), transactionCount, totalAmount);
        TransactionSummaryResponse summary = new TransactionSummaryResponse(message.getReceiverId(), message.getEventId(), transactionCount, totalAmount);
        producer.sendTransactionSummary(summary);
    }
}


