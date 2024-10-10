package com.yoyo.transaction.domain.transaction.service;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.TransactionException;
import com.yoyo.common.kafka.dto.*;
import com.yoyo.common.kafka.dto.TransactionSelfRelationDTO.ResponseFromMember;
import com.yoyo.common.kafka.dto.UpdateRelationDTO;
import com.yoyo.transaction.domain.ocr.dto.OcrConfirmDTO;
import com.yoyo.transaction.domain.transaction.dto.FindTransactionDTO;
import com.yoyo.transaction.domain.transaction.dto.TransactionCreateDTO;
import com.yoyo.transaction.domain.transaction.dto.UpdateTransactionDTO;
import com.yoyo.transaction.domain.transaction.producer.TransactionProducer;
import com.yoyo.transaction.domain.transaction.repository.TransactionRepository;
import com.yoyo.transaction.entity.RelationType;
import com.yoyo.transaction.entity.Transaction;
import com.yoyo.transaction.entity.TransactionType;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionProducer transactionProducer;
    private final Map<Long, CompletableFuture<ResponseFromMember>> summaries = new ConcurrentHashMap<>();
    private final Map<Long, CompletableFuture<EventResponseDTO>> names = new ConcurrentHashMap<>();
    private final Map<Long, CompletableFuture<OcrRegister.OcrList>> futureMap = new ConcurrentHashMap<>();

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public UpdateTransactionDTO.Response updateTransaction(Long memberId, Long transactionId,
                                                           UpdateTransactionDTO.Request request) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_TRANSACTION));
        UpdateRelationDTO.Request updateRequest = UpdateRelationDTO.Request.builder()
                                                                           .memberId(memberId)
                                                                           .relationId(request.getRelationId())
                                                                           .oppositeId(request.getOppositeId())
                                                                           .name(request.getName())
                                                                           .relationType(
                                                                                   request.getRelationType().toString())
                                                                           .amount(request.getAmount())
                                                                           .build();
        transactionProducer.sendRelationUpdate(updateRequest);
        transaction.setSenderName(request.getName());
        transaction.setRelationType(request.getRelationType());
        transaction.setIsRegister(true);
        Transaction newTransaction = transactionRepository.save(transaction);
        return new UpdateTransactionDTO.Response(newTransaction.getTransactionId(), newTransaction.getEventName(),
                                                 newTransaction.getUpdatedAt(), newTransaction.getMemo(),
                                                 newTransaction.getAmount());
    }

    /**
     * 요요 거래내역 직접 등록
     */
    public void createTransactionSelf(Long memberId, TransactionCreateDTO.Request request) {

        // 1. 친구관계 수정 (+ 등록되지 않은 회원은 회원 등록)
        ResponseFromMember response = updateTransactionRelation(memberId, request);
        request.setRelationType(response.getRelationType());

        // 2. 거래내역 저장
        Map<String, Object[]> infoMap = extractTransactionRelationInfo(response,
                TransactionType.valueOf(request.getTransactionType()));
        // 2.1 event Id 있을 시 event Name 불러옴
        if (request.getEventId() != 0) {
            request.setEventName(getNameFromEvent(request.getEventId()));
        }
        Transaction selfTransaction = toTransactionEntity(infoMap, request);

        transactionRepository.save(selfTransaction);
    }

    public void createTransactionOCR(Long memberId, Long eventId, List<OcrConfirmDTO> requestOCR) {
        String eventName = getNameFromEvent(eventId);
        List<OcrRegister> ocrRegisterList = new ArrayList<>();

        for (OcrConfirmDTO dto : requestOCR) {
            OcrRegister register = OcrRegister.builder()
                                              .memberId(memberId)
                                              .oppositeId(dto.getMemberId())
                                              .amount(dto.getAmount())
                                              .oppositeName(dto.getName())
                                              .description(dto.getDescription())
                                              .relationType(dto.getRelationType())
                                              .build();
            ocrRegisterList.add(register);
        }

        OcrRegister.OcrList ocrListToSend = OcrRegister.OcrList.builder().ocrList(ocrRegisterList).build();
        transactionProducer.sendOCRRegister(ocrListToSend);

        CompletableFuture<OcrRegister.OcrList> future = new CompletableFuture<>();
        futureMap.put(memberId, future);

        try {
            OcrRegister.OcrList ocrListFromKafka = future.get(10, TimeUnit.SECONDS);
            for (OcrRegister register : ocrListFromKafka.getOcrList()) {
                Transaction transaction = Transaction.builder()
                                                     .receiverId(memberId)
                                                     .senderId(register.getOppositeId())
                                                     .senderName(register.getOppositeName())
                                                     .amount(register.getAmount())
                                                     .eventId(eventId)
                                                     .eventName(eventName)
                                                     .relationType(RelationType.valueOf(
                                                             register.getRelationType().toUpperCase()))
                                                     .isRegister(true)
                                                     .transactionType(TransactionType.RECEIVE)
                                                     .build();
                transactionRepository.save(transaction);
            }
        } catch (Exception e) {
            throw new TransactionException(ErrorCode.KAFKA_ERROR);
        } finally {
            futureMap.remove(memberId);
        }
    }


    @KafkaListener(topics = "ocr-list-topic", concurrency = "3")
    public void getOcrList(OcrRegister.OcrList ocrListFromKafka) {
        Long memberId = ocrListFromKafka.getOcrList().get(0).getMemberId();

        CompletableFuture<OcrRegister.OcrList> future = futureMap.get(memberId);

        if (future != null) {
            future.complete(ocrListFromKafka);
        } else {
            log.error("No CompletableFuture: {}", memberId);
        }
    }


    /*
     * event Id로 event Name을 가져옴
     * */
    public String getNameFromEvent(Long eventId) {
        transactionProducer.getEventNameByEventId(eventId);
        CompletableFuture<EventResponseDTO> future = new CompletableFuture<>();
        names.put(eventId, future);
        String name;
        try {
            name = future.get(10, TimeUnit.SECONDS).getName();
        } catch (Exception e) {
            throw new TransactionException(ErrorCode.KAFKA_ERROR);
        }
        return name;
    }

    /**
     * 1. 친구관계 수정 요청
     *
     * @return 회원 id, 이름, 상대 id, 이름 반환
     */
    private ResponseFromMember updateTransactionRelation(Long memberId,
                                                         TransactionCreateDTO.Request request) {

        TransactionSelfRelationDTO.RequestToMember requestToMember = TransactionSelfRelationDTO.RequestToMember.of(
                memberId, request.getMemberId(), request.getName(), request.getAmount(),
                String.valueOf(request.getTransactionType()), String.valueOf(request.getRelationType()),
                request.getDescription());

        // 1. 직접등록 친구관계 수정 요청 (+ 등록되지 않은 회원은 회원 등록)
        transactionProducer.sendSelfTransactionRelation(requestToMember);
        CompletableFuture<ResponseFromMember> future = new CompletableFuture<>();
        summaries.put(memberId, future); // 입력한 사람 id

        // 2. 회원 id, 이름, 상대 id, 이름 반환
        ResponseFromMember response;
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
    private Map<String, Object[]> extractTransactionRelationInfo(ResponseFromMember response,
                                                                 TransactionType transactionType) {
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
                          .transactionType(TransactionType.valueOf(request.getTransactionType()))
                          .relationType(RelationType.valueOf(request.getRelationType()))
                          .build();
    }

    public void completeMemberCheck(ResponseFromMember response) {
        CompletableFuture<ResponseFromMember> future = summaries.remove(
                response.getMemberId());
        if (future != null) {
            future.complete(response);
        }
    }

    public List<FindTransactionDTO.Response> findTransactions(Long memberId, Long eventId, String search,
                                                              String relationType, boolean isRegister) {
        String validatedSearch = (search == null || search.trim().isEmpty()) ? null : search;
        String validatedRelationType = (relationType == null || relationType.trim().isEmpty()) ? null : relationType;
        List<Transaction> transactions = transactionRepository.findByEventIdAndReceiverId(eventId, memberId);
        Collections.reverse(transactions);
        return transactions.stream().filter(transaction -> validatedSearch == null || transaction.getSenderName().contains(validatedSearch))
                .filter(transaction -> validatedRelationType == null || transaction.getRelationType().toString().equals(validatedRelationType)).filter(transaction -> transaction.getIsRegister().equals(isRegister)).map(transaction -> FindTransactionDTO.Response.builder()
                        .transactionId(transaction.getTransactionId())
                        .oppositeId(transaction.getSenderId())
                        .senderName(transaction.getSenderName())
                        .relationType(transaction.getRelationType().toString())
                        .memo(transaction.getMemo())
                        .eventName(transaction.getEventName())
                        .amount(transaction.getAmount())
                        .time(transaction.getUpdatedAt() != null ? transaction.getUpdatedAt() : transaction.getCreatedAt())
                        .build()).collect(Collectors.toList());
    }

    public Map<String, Object> findTransactions(Long memberId, Long oppositeId) {
        List<Transaction> sendTransaction = transactionRepository.findAllBySenderIdAndReceiverId(memberId, oppositeId);
        List<Transaction> receiveTransaction = transactionRepository.findAllBySenderIdAndReceiverId(oppositeId, memberId);

        List<FindTransactionDTO.RelationReceiveResponse> receiveResponse = receiveTransaction.isEmpty() ? Collections.emptyList() :
                receiveTransaction.stream().map(transaction -> FindTransactionDTO.RelationReceiveResponse.builder()
                                .transactionId(transaction.getTransactionId())
                                .senderName(transaction.getSenderName())
                                .relationType(transaction.getRelationType().toString())
                                .memo(transaction.getMemo())
                                .amount(transaction.getAmount())
                                .time(transaction.getUpdatedAt() != null ? transaction.getUpdatedAt() : transaction.getCreatedAt()).build())
                        .toList();
        List<FindTransactionDTO.RelationSendResponse> sendResponse =
                sendTransaction.isEmpty() ? Collections.emptyList() : sendTransaction.stream().map(transaction -> FindTransactionDTO.RelationSendResponse.builder()
                        .transactionId(transaction.getTransactionId())
                        .receiveName(transaction.getReceiverName())
                        .relationType(transaction.getRelationType().toString())
                        .memo(transaction.getMemo())
                        .amount(transaction.getAmount())
                        .time(transaction.getUpdatedAt() != null ? transaction.getUpdatedAt() : transaction.getCreatedAt())
                        .build()).toList();
        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("receive", receiveResponse);
        responseMap.put("send", sendResponse);
        return responseMap;
    }

    public void completeEventName(EventResponseDTO response) {
        CompletableFuture<EventResponseDTO> future = names.remove(response.getEventId());
        if (future != null) {
            future.complete(response);
        }
    }
}