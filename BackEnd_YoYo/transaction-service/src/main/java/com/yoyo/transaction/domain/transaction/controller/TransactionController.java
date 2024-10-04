package com.yoyo.transaction.domain.transaction.controller;

import com.yoyo.common.dto.response.BodyValidationExceptionResopnse;
import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.response.ApiResponse;
import com.yoyo.transaction.domain.transaction.dto.FindTransactionDTO;
import com.yoyo.transaction.domain.transaction.dto.TransactionCreateDTO;
import com.yoyo.transaction.domain.transaction.dto.UpdateTransactionDTO;
import com.yoyo.transaction.domain.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/yoyo/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * 보냈어요 받았어요 직접 등록
     **/
    @PostMapping
    @Operation(summary = "요요 거래내역 직접 등록", description = "요요 거래내역 직접 등록")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "거래내역 등록 성공",
                                                                 content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "요청 dto 필드값 오류",
                                                                 content = @Content(schema = @Schema(implementation = BodyValidationExceptionResopnse.class))),
    })
    public ResponseEntity<?> createTransactionSelf(@RequestHeader("memberId") Long memberId,
                                                   @RequestBody @Valid TransactionCreateDTO.Request request) {
        transactionService.createTransactionSelf(memberId, request);
        CommonResponse response = CommonResponse.of(true, "요요 거래내역이 등록되었습니다.");

        ApiResponse<CommonResponse> res = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "직접등록 성공",
                response

        );
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "삭제 완료",
                null
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body(response);
    }

    @PatchMapping("/{transactionId}")
    public ResponseEntity<?> updateTransaction(@RequestHeader("memberId") Long memberId, @PathVariable Long transactionId, @RequestBody UpdateTransactionDTO.Request request) {
        ApiResponse<UpdateTransactionDTO.Response> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "수정 완료",
                transactionService.updateTransaction(memberId, transactionId, request)
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<?> getTransactions(@RequestHeader("memberId") Long memberId, @PathVariable("eventId") Long eventId,
                                             @RequestParam(required = false) String search,
                                             @RequestParam(required = false) String relationType,
                                             @RequestParam(defaultValue = "true") boolean isRegister) {
        ApiResponse<List<FindTransactionDTO.Response>> response;
        List<FindTransactionDTO.Response> transactions = transactionService.findTransactions(memberId, eventId, search, relationType, isRegister);
        if (transactions.isEmpty()) {
            response = new ApiResponse<>(
                    HttpStatus.NO_CONTENT.value(),
                    "데이터 없음",
                    transactions
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body(response);
        }
        response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "리스트 필터 조회",
                transactions
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }
    @GetMapping("/relation/test-up/{oppositeId}")
    public ResponseEntity<?> getTransactionsUp(@RequestHeader("memberId") Long memberId, @PathVariable("oppositeId") Long oppositeId) {
        ApiResponse<Map<String, Object>> response;
        Map<String, Object> transactions = transactionService.findTransactionsUp(memberId, oppositeId);
        if (transactions.isEmpty()) {
            response = new ApiResponse<>(
                    HttpStatus.NO_CONTENT.value(),
                    "데이터 없음",
                    transactions
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body(response);
        }
        response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "상대와의 거래내역",
                transactions
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }
    @GetMapping("/relation/test-down/{oppositeId}")
    public ResponseEntity<?> getTransactionsDown(@RequestHeader("memberId") Long memberId, @PathVariable("oppositeId") Long oppositeId) {
        ApiResponse<Map<String, Object>> response;
        Map<String, Object> transactions = transactionService.findTransactionsDown(memberId, oppositeId);
        if (transactions.isEmpty()) {
            response = new ApiResponse<>(
                    HttpStatus.NO_CONTENT.value(),
                    "데이터 없음",
                    transactions
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body(response);
        }
        response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "상대와의 거래내역",
                transactions
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }
    @GetMapping("/relation/{oppositeId}")
    public ResponseEntity<?> getTransactions(@RequestHeader("memberId") Long memberId, @PathVariable("oppositeId") Long oppositeId) {
        ApiResponse<Map<String, Object>> response;
        Map<String, Object> transactions = transactionService.findTransactions(memberId, oppositeId);
        if (transactions.isEmpty()) {
            response = new ApiResponse<>(
                    HttpStatus.NO_CONTENT.value(),
                    "데이터 없음",
                    transactions
            );
            return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body(response);
        }
        response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "상대와의 거래내역",
                transactions
        );
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }
}
