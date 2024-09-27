package com.yoyo.transaction.domain.transaction.controller;

import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.response.ApiResponse;
import com.yoyo.transaction.domain.transaction.dto.TransactionCreateDTO;
import com.yoyo.transaction.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yoyo/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * 보냈어요 받았어요 직접 등록
     * **/
    @PostMapping
    public ResponseEntity<?> createTransactionSelf(@RequestHeader("memberId") Long memberId,
            @RequestBody TransactionCreateDTO.Request request) {
        transactionService.createTransactionSelf(memberId, request);
        CommonResponse response = CommonResponse.of(true, "요요 거래내역이 등록되었습니다.");

        ApiResponse<CommonResponse> res = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "직접등록 성공",
                response

        );
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


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
