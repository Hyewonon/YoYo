package com.yoyo.transaction.domain.transaction.consumer;

import com.yoyo.common.kafka.dto.*;
import com.yoyo.transaction.domain.transaction.producer.TransactionProducer;
import com.yoyo.transaction.domain.transaction.repository.TransactionRepository;
import com.yoyo.transaction.domain.transaction.service.TransactionService;
import com.yoyo.transaction.entity.RelationType;
import com.yoyo.transaction.entity.Transaction;
import com.yoyo.transaction.entity.TransactionType;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionConsumer {

    private final String UPDATE_TRANSACTION_TOPIC = "pay-update-transaction-topic";
    private final String SEND_TRANSACTION_SELF_RELATION_TOPIC = "send-transaction-self-relation-topic";
    private final String SEND_EVENT_NAME = "send-event-name";

    private final String UPDATE_TRANSACTION_NO_MEMBER_TOPIC = "pay-update-transaction-no-member-topic";
    private final TransactionRepository transactionRepository;
    private final TransactionProducer producer;
    private final TransactionService transactionService;

    @KafkaListener(topics = "payment-success", concurrency = "3")
    public void setTransaction(CreateTransactionDTO request) {
        Transaction jpaEntity = Transaction.builder()
                .senderName(request.getSenderName())
                .receiverId(request.getReceiverId())
                .receiverName(request.getReceiverName())
                .eventId(request.getEventId())
                .eventName(request.getTitle())
                .amount(request.getAmount())
                .memo(request.getMemo())
                .relationType(RelationType.NONE)
                .build();
        transactionRepository.save(jpaEntity);
    }

    @KafkaListener(topics = "transaction-topic", concurrency = "3")
    public void getEventInformation(AmountRequestDTO message) {
        List<Transaction> transactions = transactionRepository.findByReceiverIdAndEventId(message.getReceiverId(),
                message.getEventId());
        int transactionCount = transactions.size();
        long totalAmount = transactions.stream().mapToLong(Transaction::getAmount).sum();
        AmountResponseDTO summary = new AmountResponseDTO(message.getReceiverId(), message.getEventId(),
                transactionCount, totalAmount);
        producer.sendTransactionSummary(summary);
    }

    @KafkaListener(topics = UPDATE_TRANSACTION_TOPIC, concurrency = "3")
    public Transaction createTransactionForPay(PayInfoRequestToTransactionDTO request) {

        List<Transaction> existingTransactions = transactionRepository.findBySenderIdAndReceiverId(request.getSenderId(), request.getReceiverId());
        RelationType relationType = existingTransactions.isEmpty()
                                    ? RelationType.NONE
                                    : existingTransactions.get(0).getRelationType(); // 첫 번째 거래의 관계 유형을 가져옴

        String eventName = (request.getTitle().isEmpty()) ? transactionService.getNameFromEvent(request.getEventId()) : request.getTitle();

        Transaction transaction = Transaction.builder()
                .isRegister(true)
                .senderId(request.getSenderId())
                .senderName(request.getSenderName())
                .receiverId(request.getReceiverId())
                .receiverName(request.getReceiverName())
                .eventId(request.getEventId())
                .eventId(request.getEventId())
                .eventName(eventName)
                .amount(request.getAmount())
                .transactionType(TransactionType.AUTO)
                .relationType(relationType)
                .build();
        return transactionService.createTransaction(transaction);
    }

    /**
     * 요요 거래내역 직접 등록 시, 친구 관계 수정 후 상대 회원 여부 응답 받기
     */
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
                .relationType(RelationType.NONE)
                .build();
        transactionService.createTransaction(transaction);
    }

    @KafkaListener(topics = "update-transaction-relation-type-topic", concurrency = "3")
    public void updateTransactionRelationType(UpdateTransactionRelationTypeDTO updateTransactionRelationTypeDTO) {
        List<Transaction> existingTransaction1 = transactionRepository.findAllBySenderIdAndReceiverId(updateTransactionRelationTypeDTO.getMemberId(), updateTransactionRelationTypeDTO.getOppositeId());
        List<Transaction> existingTransaction2 = transactionRepository.findAllBySenderIdAndReceiverId(updateTransactionRelationTypeDTO.getOppositeId(), updateTransactionRelationTypeDTO.getMemberId());
        existingTransaction1.forEach(transaction -> {
            transaction.setRelationType(RelationType.valueOf(updateTransactionRelationTypeDTO.getRelationType()));
            transactionRepository.save(transaction);
        });
        existingTransaction2.forEach(transaction -> {
            transaction.setRelationType(RelationType.valueOf(updateTransactionRelationTypeDTO.getRelationType()));
            transactionRepository.save(transaction);
        });
    }

    @KafkaListener(topics = SEND_EVENT_NAME, concurrency = "3")
    public void getMemberName(EventResponseDTO response) {
        transactionService.completeEventName(response);
    }
}


