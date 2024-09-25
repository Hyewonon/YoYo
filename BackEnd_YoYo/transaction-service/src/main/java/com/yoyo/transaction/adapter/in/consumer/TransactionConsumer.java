package com.yoyo.transaction.adapter.in.consumer;

import com.yoyo.common.kafka.dto.CreateTransactionDTO;
import com.yoyo.common.kafka.dto.AmountRequestDTO;
import com.yoyo.common.kafka.dto.AmountResponseDTO;
import com.yoyo.transaction.adapter.out.persistence.SpringDataTransactionRepository;
import com.yoyo.transaction.adapter.out.persistence.TransactionJpaEntity;
import com.yoyo.transaction.adapter.out.producer.TransactionProducer;
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

    @KafkaListener(topics = "payment-success", concurrency = "3")
    public void setTransaction(CreateTransactionDTO request) {
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

    @KafkaListener(topics = "transaction-topic", concurrency = "3")
    public void getEventInformation(AmountRequestDTO message) {
        List<TransactionJpaEntity> transactions = transactionRepository.findByReceiverIdAndEventId(message.getReceiverId(), message.getEventId());
        int transactionCount = transactions.size();
        long totalAmount = transactions.stream().mapToLong(TransactionJpaEntity::getAmount).sum();
        log.info("Event ID: {}, Receiver ID: {}, Total Transactions: {}, Total Amount: {}",
                message.getEventId(), message.getReceiverId(), transactionCount, totalAmount);
        AmountResponseDTO summary = new AmountResponseDTO(message.getReceiverId(), message.getEventId(), transactionCount, totalAmount);
        producer.sendTransactionSummary(summary);
    }
}


