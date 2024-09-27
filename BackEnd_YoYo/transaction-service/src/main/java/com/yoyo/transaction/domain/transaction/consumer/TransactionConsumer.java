package com.yoyo.transaction.domain.transaction.consumer;

import com.yoyo.common.kafka.dto.AmountRequestDTO;
import com.yoyo.common.kafka.dto.AmountResponseDTO;
import com.yoyo.common.kafka.dto.CreateTransactionDTO;
import com.yoyo.common.kafka.dto.PayInfoDTO;
import com.yoyo.common.kafka.dto.TransactionSelfRelationDTO;
import com.yoyo.common.kafka.dto.PaymentDTO;
import com.yoyo.transaction.domain.transaction.producer.TransactionProducer;
import com.yoyo.transaction.domain.transaction.repository.TransactionRepository;
import com.yoyo.transaction.domain.transaction.service.TransactionService;
import com.yoyo.transaction.entity.Transaction;
import com.yoyo.transaction.entity.TransactionType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionConsumer {

    private final String UPDATE_TRANSACTION_TOPIC = "pay-update-transaction-topic";
    private final String SEND_TRANSACTION_SELF_RELATION_TOPIC = "send-transaction-self-relation-topic";

    private final String UPDATE_TRANSACTION_NO_MEMBER_TOPIC = "pay-update-transaction-no-member-topic";
    private final TransactionRepository transactionRepository;
    private final TransactionProducer producer;
    private final TransactionService transactionService;

    @KafkaListener(topics = "payment-success", concurrency = "3")
    public void setTransaction(CreateTransactionDTO request) {
        Transaction jpaEntity = new Transaction(
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
        List<Transaction> transactions = transactionRepository.findByReceiverIdAndEventId(message.getReceiverId(),
                                                                                          message.getEventId());
        int transactionCount = transactions.size();
        long totalAmount = transactions.stream().mapToLong(Transaction::getAmount).sum();
        log.info("Event ID: {}, Receiver ID: {}, Total Transactions: {}, Total Amount: {}",
                 message.getEventId(), message.getReceiverId(), transactionCount, totalAmount);
        AmountResponseDTO summary = new AmountResponseDTO(message.getReceiverId(), message.getEventId(),
                                                          transactionCount, totalAmount);
        producer.sendTransactionSummary(summary);
    }

    @KafkaListener(topics = UPDATE_TRANSACTION_TOPIC, concurrency = "3")
    public Transaction createTransactionForPay(PayInfoDTO.RequestToTransaction request) {
        Transaction transaction = Transaction.builder()
                                             .senderId(request.getSenderId())
                                             .senderName(request.getSenderName())
                                             .receiverId(request.getReceiverId())
                                             .receiverName(request.getReceiverName())
                                             .eventId(request.getEventId())
                                             .eventId(request.getEventId())
                                             .eventName(request.getTitle())
                                             .amount(request.getAmount())
                                             .transactionType(TransactionType.AUTO)
                                             .build();
        return transactionService.createTransaction(transaction);
    }

    /**
     * 요요 거래내역 직접 등록 시, 친구 관계 수정 후 상대 회원 여부 응답 받기
     * */
    @KafkaListener(topics = SEND_TRANSACTION_SELF_RELATION_TOPIC, concurrency = "3")
    public void getTransactionSelfFromRelation(TransactionSelfRelationDTO.ResponseFromMember response) {
        transactionService.completeMemberCheck(response);
    }

    /*
     * 비회원이 보낸 거래 내역 저장 로직
     */
    @KafkaListener(topics = UPDATE_TRANSACTION_NO_MEMBER_TOPIC, concurrency = "3")
    public void createTransactionForPayment(PaymentDTO request) {
        Transaction transaction = Transaction.builder()
                                             .senderId(request.getSenderId())
                                             .senderName(request.getSenderName())
                                             .receiverId(request.getReceiverId())
                                             .receiverName(request.getReceiverName())
                                             .eventId(request.getEventId())
                                             .eventName(request.getTitle())
                                             .isRegister(false)
                                             .amount(request.getAmount())
                                             .memo(request.getMemo())
                                             .transactionType(TransactionType.RECEIVE)
                                             .createdAt(LocalDateTime.now())
                                             .build();
        transactionService.createTransaction(transaction);
    }
}


