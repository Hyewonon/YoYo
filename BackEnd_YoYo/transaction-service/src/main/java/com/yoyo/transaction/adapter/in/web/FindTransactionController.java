package com.yoyo.transaction.adapter.in.web;

import com.yoyo.transaction.application.port.in.FindTransactionCommand;
import com.yoyo.transaction.application.port.in.FindTransactionUseCase;
import com.yoyo.transaction.domain.Transaction;
import com.yoyo.transaction.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        ApiResponse<Transaction> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "내역 상세 조회",
                findTransactionUseCase.findTransaction(command)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 보낸 내역
    @GetMapping("/yoyo/transactions/send")
    public ResponseEntity<?> getSendTransactions(@RequestHeader("memberId") String memberId, @RequestParam Long oppositeId) {
        ApiResponse<List<Transaction>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "보낸 내역 리스트 조회",
                findTransactionUseCase.findTransactionBySenderAndReceiver(Long.parseLong(memberId), oppositeId)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 받은 내역
    @GetMapping("/yoyo/transactions/receive")
    public ResponseEntity<?> getReceiveTransactions(@RequestHeader("memberId") String memberId, @RequestParam Long oppositeId) {
        ApiResponse<List<Transaction>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "받은 내역 리스트 조회",
                findTransactionUseCase.findTransactionBySenderAndReceiver(oppositeId, Long.parseLong(memberId))
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
