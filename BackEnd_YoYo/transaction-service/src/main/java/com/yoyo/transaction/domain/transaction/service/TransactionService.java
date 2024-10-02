package com.yoyo.transaction.domain.transaction.service;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.TransactionException;
import com.yoyo.common.kafka.dto.FindDescriptionDTO;
import com.yoyo.common.kafka.dto.TransactionSelfRelationDTO;
import com.yoyo.common.kafka.dto.TransactionSelfRelationDTO.ResponseFromMember;
import com.yoyo.transaction.domain.transaction.dto.TransactionCreateDTO;
import com.yoyo.transaction.domain.transaction.producer.TransactionProducer;
import com.yoyo.transaction.domain.transaction.dto.FindTransactionDTO;
import com.yoyo.transaction.domain.transaction.dto.UpdateTransactionDTO;
import com.yoyo.transaction.domain.transaction.repository.TransactionRepository;
import com.yoyo.transaction.entity.Transaction;
import com.yoyo.transaction.entity.TransactionType;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionProducer transactionProducer;
    private final Map<Long, CompletableFuture<TransactionSelfRelationDTO.ResponseFromMember>> summaries = new ConcurrentHashMap<>();
    private final CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              TransactionProducer transactionProducer) {
        this.transactionRepository = transactionRepository;
        this.transactionProducer = transactionProducer;
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public UpdateTransactionDTO.Response updateTransaction(Long transactionId, UpdateTransactionDTO.Request request) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TRANSACTION));
        transaction.setEventName(request.getTitle());
        transaction.setAmount(request.getAmount());
        transaction.setMemo(request.getMemo());
        Transaction newTransaction = transactionRepository.save(transaction);
        return new UpdateTransactionDTO.Response(newTransaction.getTransactionId(), newTransaction.getEventName(), newTransaction.getUpdatedAt(), newTransaction.getMemo(), newTransaction.getAmount());
    }

    /**
     * 요요 거래내역 직접 등록
     */
    public void createTransactionSelf(Long memberId, TransactionCreateDTO.Request request) {

        // 1. 친구관계 수정 (+ 등록되지 않은 회원은 회원 등록)
        TransactionSelfRelationDTO.ResponseFromMember response = updateTransactionRelation(memberId, request);

        // 2. 거래내역 저장
        Map<String, Object[]> infoMap = extractTransactionRelationInfo(response, request.getTransactionType());
        Transaction selfTransaction = toTransactionEntity(infoMap, request);

        transactionRepository.save(selfTransaction);
    }

    /**
     * 1. 친구관계 수정 요청
     *
     * @return 회원 id, 이름, 상대 id, 이름 반환
     */
    private TransactionSelfRelationDTO.ResponseFromMember updateTransactionRelation(Long memberId, TransactionCreateDTO.Request request) {
        TransactionSelfRelationDTO.RequestToMember requestToMember = TransactionSelfRelationDTO.RequestToMember.of(
                memberId, request.getMemberId(), request.getName(), request.getAmount(),
                String.valueOf(request.getTransactionType()), String.valueOf(request.getRelationType()));

        // 1. 직접등록 친구관계 수정 요청 (+ 등록되지 않은 회원은 회원 등록)
        transactionProducer.sendSelfTransactionRelation(requestToMember);
        CompletableFuture<TransactionSelfRelationDTO.ResponseFromMember> future = new CompletableFuture<>();
        summaries.put(memberId, future); // 입력한 사람 id

        // 2. 회원 id, 이름, 상대 id, 이름 반환
        TransactionSelfRelationDTO.ResponseFromMember response;
        try {
            response = future.get(10, TimeUnit.SECONDS);
            return response;
        } catch (Exception e) {
            throw new TransactionException(ErrorCode.KAFKA_ERROR);
        }
    }

    /**
     * 2. 친구관계 응답에서 직접등록 수신인 발신인 정보 추출
     */
    private Map<String, Object[]> extractTransactionRelationInfo(ResponseFromMember response, TransactionType transactionType) {
        Map<String, Object[]> infoMap = new HashMap<>();
        Object[] info = new Object[2];

        if (transactionType == TransactionType.SEND) {
            info[0] = response.getMemberId();
            info[1] = response.getMemberName();
            infoMap.put("sender", info);

            info = new Object[2];
            info[0] = response.getOppositeId();
            info[1] = response.getOppositeName();
            infoMap.put("receiver", info);
        } else if (transactionType == TransactionType.RECEIVE) {
            info[0] = response.getOppositeId();
            info[1] = response.getOppositeName();
            infoMap.put("sender", info);

            info = new Object[2];
            info[0] = response.getMemberId();
            info[1] = response.getMemberName();
            infoMap.put("receiver", info);
        } else {
            throw new TransactionException(ErrorCode.NOT_FOUND);
        }
        return infoMap;
    }

    private Transaction toTransactionEntity(Map<String, Object[]> infoMap, TransactionCreateDTO.Request request) {
        return Transaction.builder()
                .senderId((Long) infoMap.get("sender")[0])
                .senderName((String) infoMap.get("sender")[1])
                .receiverId((Long) infoMap.get("receiver")[0])
                .receiverName((String) infoMap.get("receiver")[1])
                .eventId(request.getEventId())
                .eventName(request.getEventName())
                .isRegister(true)
                .amount(request.getAmount())
                .memo(request.getMemo())
                .transactionType(request.getTransactionType())
                .build();
    }

    public void completeMemberCheck(TransactionSelfRelationDTO.ResponseFromMember response) {
        CompletableFuture<TransactionSelfRelationDTO.ResponseFromMember> future = summaries.remove(response.getMemberId());
        if (future != null) {
            future.complete(response);
        }
    }

    public List<FindTransactionDTO.Response> findTransactions(Long memberId, Long eventId, String search, String relationType, boolean isRegister) {
        String validatedSearch = (search == null || search.trim().isEmpty()) ? null : search;
        String validatedRelationType = (relationType == null || relationType.trim().isEmpty()) ? null : relationType;
        List<Transaction> transactions = transactionRepository.findByEventIdAndReceiverId(eventId, memberId);
        return transactions.stream()
                .filter(transaction -> validatedSearch == null || transaction.getSenderName().contains(validatedSearch))
                .filter(transaction -> validatedRelationType == null || transaction.getRelationType().toString().equals(validatedRelationType))
                .filter(transaction -> transaction.getIsRegister().equals(isRegister))
                .map(transaction -> FindTransactionDTO.Response.builder()
                        .transactionId(transaction.getTransactionId())
                        .oppositeId(transaction.getSenderId())
                        .senderName(transaction.getSenderName())
                        .relationType(transaction.getRelationType().toString())
                        .memo(transaction.getMemo())
                        .amount(transaction.getAmount())
                        .time(transaction.getUpdatedAt() != null ? transaction.getUpdatedAt() : transaction.getCreatedAt())
                        .build()).collect(Collectors.toList());
    }

    private String description;

    public Map<String, Object> findTransactions(Long memberId, Long oppositeId) {
        CountDownLatch latch = new CountDownLatch(1);
        List<Transaction> sendTransaction = transactionRepository.findAllBySenderIdAndReceiverId(memberId, oppositeId);
        List<Transaction> receiveTransaction = transactionRepository.findAllBySenderIdAndReceiverId(oppositeId, memberId);

        String oppositeName = receiveTransaction.isEmpty() ? (sendTransaction.isEmpty() ? null : sendTransaction.get(0).getSenderName()) : receiveTransaction.get(0).getSenderName();
        FindDescriptionDTO.Request request = FindDescriptionDTO.Request.builder().memberId(memberId).oppositeId(oppositeId).build();
        transactionProducer.sendRelationDescription(request);
        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long totalReceivedAmount = receiveTransaction.stream().mapToLong(Transaction::getAmount).sum();
        long totalSentAmount = sendTransaction.stream().mapToLong(Transaction::getAmount).sum();
        List<FindTransactionDTO.RelationReceiveResponse> receiveResponse = receiveTransaction.isEmpty() ? Collections.emptyList() :
                receiveTransaction.stream().map(transaction -> FindTransactionDTO.RelationReceiveResponse.builder()
                        .transactionId(transaction.getTransactionId())
                        .senderName(transaction.getSenderName())
                        .relationType(transaction.getRelationType().toString())
                        .memo(transaction.getMemo())
                        .amount(transaction.getAmount())
                        .time(transaction.getUpdatedAt() != null ? transaction.getUpdatedAt() : transaction.getCreatedAt())
                        .build()).toList();
        List<FindTransactionDTO.RelationSendResponse> sendResponse = sendTransaction.isEmpty() ? Collections.emptyList() :
                sendTransaction.stream().map(transaction -> FindTransactionDTO.RelationSendResponse.builder()
                        .transactionId(transaction.getTransactionId())
                        .receiveName(transaction.getReceiverName())
                        .relationType(transaction.getRelationType().toString())
                        .memo(transaction.getMemo())
                        .amount(transaction.getAmount())
                        .time(transaction.getUpdatedAt() != null ? transaction.getUpdatedAt() : transaction.getCreatedAt())
                        .build()).toList();
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("oppositeName", oppositeName);
        responseMap.put("description", description);
        responseMap.put("totalReceivedAmount", totalReceivedAmount);
        responseMap.put("totalSentAmount", totalSentAmount);
        responseMap.put("receive", receiveResponse);
        responseMap.put("send", sendResponse);
        return responseMap;
    }

    @KafkaListener(topics = "description-response-topic", concurrency = "3")
    public void getDescriptionResponse(FindDescriptionDTO.Response response) {
        this.description = response.getDescription();
        latch.countDown();
    }
}