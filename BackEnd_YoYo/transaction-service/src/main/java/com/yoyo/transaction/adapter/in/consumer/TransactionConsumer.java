package com.yoyo.transaction.adapter.in.consumer;

import com.yoyo.common.kafka.dto.CreateTransactionDTO;
import com.yoyo.common.kafka.dto.AmountRequestDTO;
import com.yoyo.common.kafka.dto.AmountResponseDTO;
import com.yoyo.common.kafka.dto.PayInfoDTO;
import com.yoyo.transaction.adapter.out.persistence.SpringDataTransactionRepository;
import com.yoyo.transaction.adapter.out.persistence.TransactionJpaEntity;
import com.yoyo.transaction.adapter.out.persistence.TransactionType;
import com.yoyo.transaction.adapter.out.producer.TransactionProducer;
import com.yoyo.transaction.application.port.in.RegisterTransactionCommand;
import com.yoyo.transaction.application.service.RegisterTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionConsumer {

    private final String UPDATE_TRANSACTION_TOPIC = "pay-update-transaction-topic";
    private final String GROUP_ID = "transaction-service";

    private final SpringDataTransactionRepository transactionRepository;
    private final TransactionProducer producer;
    private final RegisterTransactionService registerTransactionService;

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

    @KafkaListener(topics = UPDATE_TRANSACTION_TOPIC, groupId = GROUP_ID)
    public void createTransactionForPay(PayInfoDTO.RequestToTransaction request) {
        registerTransactionService.registerTransaction(of(request));
    }

    private RegisterTransactionCommand of(PayInfoDTO.RequestToTransaction request){
        return RegisterTransactionCommand.builder()
                                         .senderId(request.getSenderId())
                                         .senderName(request.getSenderName())
                                         .receiverId(request.getReceiverId())
                                         .receiverName(request.getReceiverName())
                                         .eventId(request.getEventId())
                                         .eventName(request.getTitle())
                                         .amount(request.getAmount())
                                         .transactionType(TransactionType.AUTO)
                                         .memo("")
                                         .build();
    }

}


