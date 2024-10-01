package com.yoyo.transaction.domain.ocr.controller;

import com.yoyo.common.kafka.dto.TransactionDTO;
import com.yoyo.common.response.ApiResponse;
import com.yoyo.transaction.domain.ocr.service.OcrService;
import com.yoyo.transaction.domain.transaction.dto.TransactionCreateDTO;
import com.yoyo.transaction.domain.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yoyo/transactions")
public class OcrController {

    private final OcrService ocrService;
    private final TransactionService transactionService;

    @PostMapping("/ocr-image")
    public ResponseEntity<?> getText(@RequestPart MultipartFile imageFile, @RequestHeader("memberId")Long memberId) {
        ApiResponse<List<TransactionDTO.MatchRelation>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "OCR 탐색",
                ocrService.getText(imageFile, memberId)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/ocr-confirm")
    public ResponseEntity<?> confirm(@RequestHeader("memberId") Long memberId, List<TransactionCreateDTO.Request> request) {
        for (TransactionCreateDTO.Request requestDto : request) {
            transactionService.createTransactionSelf(memberId, requestDto);
        }
        ApiResponse<List<TransactionCreateDTO.Request>> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "OCR 등록 성공",
                request
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
