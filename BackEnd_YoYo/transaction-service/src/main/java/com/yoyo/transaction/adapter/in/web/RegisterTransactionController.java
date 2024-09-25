package com.yoyo.transaction.adapter.in.web;

import com.yoyo.common.annotation.WebAdapter;
import com.yoyo.transaction.adapter.out.persistence.TransactionType;
import com.yoyo.common.kafka.dto.IncreaseAmountDTO;
import com.yoyo.transaction.adapter.out.producer.TransactionProducer;
import com.yoyo.transaction.application.port.in.RegisterTransactionCommand;
import com.yoyo.transaction.application.port.in.RegisterTransactionUseCase;
import com.yoyo.transaction.domain.Transaction;
import com.yoyo.transaction.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterTransactionController {

    private final RegisterTransactionUseCase registerTransactionUseCase;
    private final TransactionProducer transactionProducer;

    // 보냈어요 등록
    @PostMapping("/yoyo/transactions/send/register")
    public ResponseEntity<?> sendTransaction(@RequestHeader("memberId") String memberId, @RequestBody RegisterSendRequest registerSendRequest) {
        RegisterTransactionCommand command = RegisterTransactionCommand.builder()
                .senderId(Long.parseLong(memberId))
                .receiverId(registerSendRequest.getReceiverId())
                .receiverName(registerSendRequest.getReceiverName())
                .relationId(registerSendRequest.getRelationId())
                .eventId(registerSendRequest.getEventId())
                .eventName(registerSendRequest.getEventName())
                .amount(registerSendRequest.getAmount())
                .memo(registerSendRequest.getMemo())
                .transactionType(TransactionType.SEND)
                .build();
        IncreaseAmountDTO amount = IncreaseAmountDTO.builder()
                .memberId(Long.parseLong(memberId))
                .oppositeId(registerSendRequest.getReceiverId())
                .amount(registerSendRequest.getAmount())
                .send(true)
                .build();
        transactionProducer.sendRelation(amount);
        ApiResponse<Transaction> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "보낸 내역 등록",
                registerTransactionUseCase.registerTransaction(command)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 받았어요 등록
    @PostMapping("/yoyo/transactions/receive/register")
    public ResponseEntity<?> receiveTransaction(@RequestHeader("memberId") String memberId, @RequestBody RegisterReceiveRequest registerReceiveRequest) {
        RegisterTransactionCommand command = RegisterTransactionCommand.builder()
                .senderId(registerReceiveRequest.getSenderId())
                .senderName(registerReceiveRequest.getSenderName())
                .receiverId(Long.parseLong(memberId))
                .relationId(registerReceiveRequest.getRelationId())
                .eventName(registerReceiveRequest.getEventName())
                .amount(registerReceiveRequest.getAmount())
                .memo(registerReceiveRequest.getMemo())
                .transactionType(TransactionType.RECEIVE)
                .build();
        IncreaseAmountDTO amount = IncreaseAmountDTO.builder()
                .memberId(Long.parseLong(memberId))
                .oppositeId(registerReceiveRequest.getSenderId())
                .amount(registerReceiveRequest.getAmount())
                .send(false)
                .build();
        transactionProducer.sendRelation(amount);
        ApiResponse<Transaction> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "받은 내역 등록",
                registerTransactionUseCase.registerTransaction(command)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
