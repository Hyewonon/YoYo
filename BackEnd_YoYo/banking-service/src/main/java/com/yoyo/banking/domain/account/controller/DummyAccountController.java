package com.yoyo.banking.domain.account.controller;

import com.yoyo.banking.domain.account.dto.account.AccountAuthDTO;
import com.yoyo.banking.domain.account.dto.account.DummyAccountDTO;
import com.yoyo.banking.domain.account.service.DummyAccountService;
import com.yoyo.banking.domain.account.service.SsafyBankService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yoyo/account")
@RequiredArgsConstructor
@Slf4j
public class DummyAccountController {

    private final SsafyBankService ssafyBankService;
    private final DummyAccountService dummyAccountService;

    //임시 멤버
    private Long memberId = 999999999L;

    /**
     *  TODO : [ssafy 금융 API] 더미 계좌를 생성함.
     * */
    @PostMapping("/dummy-account")
    @Operation(summary = "더미 계좌 생성", description = "Ssafy 서버에 더미 계좌를 생성한다.")
    ResponseEntity<?> createDummyAccount() {
        Long currentMemberId = memberId;
        return ssafyBankService.createDummyAccount(currentMemberId);
    }

    /**
     *  [ssafy 금융 API] 거래 내역 확인 (1원 송금 확인용)
     * */
    @GetMapping("/dummy-transaction")
    @Operation(summary = "더미 계좌 거래 내역 조회", description = "더미 계좌 거래 내역을 조회한다. (1원 송금 확인용)")
    ResponseEntity<?> getDummyAccountTransaction(@RequestBody @Valid AccountAuthDTO.Request request){
        Long currentMemberId = memberId;
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return ssafyBankService.getDummyAccountTransaction(request, currentMemberId, today);
    }

    /**
     *  [ssafy 금융 API] 계좌 돈 입금
     * */
    @PostMapping("/deposit")
    @Operation(summary = "계좌 돈 입금", description = "ssafy 서버에 있는 더미 계좌에 돈을 입금한다.")
    ResponseEntity<?> depositDummyAccount(@RequestBody DummyAccountDTO.Request request){
        Long currentMemberId = memberId;
        return ssafyBankService.updateDemandDeposit(currentMemberId, request.getAmount(), true);
    }

    /**
     *  [ssafy 금융 API] 계좌 목록 조회함.
     * */
//    @GetMapping("/dummy-account")
//    @Operation(summary = "더미 계좌 목록 조회", description = "Ssafy 서버에 계좌 목록을 조회한다.")
//    ResponseEntity<?> getDummyAccounts() {
//        Long currentMemberId = memberId;
//        return ssafyBankService.getDummyAccount(currentMemberId);
//    }

    /**
     * 은행코드 db에 저장
     * */
    @PostMapping("/dummy-bank")
    @Operation(summary = "은행 db에 저장", description = "db에 은행코드를 저장해요.")
    void createDummyBank() {
//        log.info("-----------------은행코드 저장--------------------");
        dummyAccountService.은행_데이터_생성();
    }
}
