package com.yoyo.transaction.domain.transaction.controller;

import com.yoyo.common.response.ApiResponse;
import com.yoyo.transaction.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yoyo/members")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @DeleteMapping("{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "삭제 완료",
                null
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body(response);
    }
}
