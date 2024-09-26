package com.yoyo.banking.domain.account.controller;

import com.yoyo.banking.domain.account.dto.pay.PayDTO;
import com.yoyo.banking.domain.account.dto.pay.PayTransferDTO;
import com.yoyo.banking.domain.account.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yoyo/pay")
@RequiredArgsConstructor
@Slf4j
public class PayController {

    private final PayService payService;
    //임시 멤버
//    private Long memberId = 999999999L;
    private Long memberId = 999999998L;

    /**
     * * 페이 머니 충전
     * <p>
     * - 중앙계좌로 이체 요청
     * - 페이 거래내역 저장
     * */
    @PostMapping("/charge")
    @Operation(summary = "페이 머니 충전", description = "페이 머니를 충전한다. (대금 출금 요청)")
    ResponseEntity<?> chargePayment(@RequestBody PayDTO.Request request) {
        Long currentMemberId = memberId;
        return payService.chargeOrRefundPayBalance(request, currentMemberId, false);
    }
    /**
     * * 페이 머니 환불
     * <p>
     * - 중앙계좌에서 이체 요청
     * - 페이 거래내역 저장
     * */
    @PostMapping("/refund")
    @Operation(summary = "페이 머니 환불", description = "페이 머니를 환불한다. (대금 입금 요청)")
    ResponseEntity<?> refundPayment(@RequestBody PayDTO.Request request) {
        Long currentMemberId = memberId;
        return payService.chargeOrRefundPayBalance(request, currentMemberId, true);
    }

    /**
     * * TODO : 페이 머니 송금
     * <p>
     * - 상대 페이 ++
     * - 페이 거래내역 저장 (내 거래 내역, 친구 거래내역 둘다)
     * */
    @PostMapping("/transfer")
    @Operation(summary = "페이 거래", description = "친구 페이머니로 송금")
    ResponseEntity<?> transferPayment(@RequestBody PayTransferDTO.Request request) {
        Long currentMemberId = memberId;
        return payService.transferPayment(request, currentMemberId);
    }

    /**
     * * 페이 거래 내역 조회
     * <p>
     * - 입금 : 입금 (+ 충전)
     * - 출금 : 출금 (+ 환불)
     * */
    @GetMapping("/transaction")
    @Operation(summary = "페이 거래 내역 조회", description = "페이 거래 내역 조회")
    ResponseEntity<?> getPayTransaction(@RequestParam
                                        @Parameter(name="payType", description = "거래 타입", example = "DEPOSIT/WITHDRAW", required = true)
                                        String transactionType) {
        Long currentMemberId = memberId;
        return payService.getPayTransactions(transactionType, currentMemberId);
    }

    /**
     * * 페이 잔액 조회
     * */
    @GetMapping
    @Operation(summary = "페이 잔액 조회", description = "페이 잔액 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "페이 잔액 조회 성공",
                         content = @Content(schema = @Schema(implementation = PayDTO.Response.class))),
            @ApiResponse(responseCode = "400", description = "등록되지 않은 계좌입니다.")
    })
    public ResponseEntity<?> getPayBalance(){
        Long currentMemberId = memberId;
        return payService.getPayBalance(currentMemberId);
    }
}
