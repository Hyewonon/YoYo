package com.yoyo.transaction.adapter.in.web;

import com.yoyo.transaction.application.port.in.DeleteTransactionUseCase;
import com.yoyo.transaction.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteTransactionController {
    private final DeleteTransactionUseCase deleteTransactionUseCase;

    @DeleteMapping("/yoyo/transactions/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transactionId") Long transactionId) {
        deleteTransactionUseCase.deleteTransaction(transactionId);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "내역 삭제 성공",
                null
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
