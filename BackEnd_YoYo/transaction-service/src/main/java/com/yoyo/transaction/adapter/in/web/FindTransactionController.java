package com.yoyo.transaction.adapter.in.web;

import com.yoyo.transaction.application.port.in.FindTransactionCommand;
import com.yoyo.transaction.application.port.in.FindTransactionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class FindTransactionController {
    private final FindTransactionUseCase findTransactionUseCase;
    // 상세 보기
    @GetMapping("/yoyo/transactions/{transactionId}")
    public ResponseEntity<?> findTransaction(@PathVariable("transactionId") Long transactionId) {
        FindTransactionCommand command = FindTransactionCommand.builder()
                .transactionId(transactionId)
                .build();
        return ResponseEntity.ok(findTransactionUseCase.findTransaction(command));
    }
    // 보낸 내역
    @GetMapping("/yoyo/transactions/send")
    public ResponseEntity<?> getSendTransactions(Long memberId) {
        return ResponseEntity.ok(findTransactionUseCase.findTransactionBySenderId(memberId));
    }
    // 받은 내역
    @GetMapping("/yoyo/transactions/receive")
    public ResponseEntity<?> getReceiveTransactions(Long memberId) {
        return ResponseEntity.ok(findTransactionUseCase.findTransactionByReceiverId(memberId));
    }
}
