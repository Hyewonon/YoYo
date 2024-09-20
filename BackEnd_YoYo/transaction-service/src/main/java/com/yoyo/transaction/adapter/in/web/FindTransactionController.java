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

    @GetMapping("/yoyo/transactions/{transactionId}")
    public ResponseEntity<?> findTransaction(@PathVariable("transactionId") Long transactionId) {
        FindTransactionCommand command = FindTransactionCommand.builder()
                .transactionId(transactionId)
                .build();
        return ResponseEntity.ok(findTransactionUseCase.findTransaction(command));
    }
}
