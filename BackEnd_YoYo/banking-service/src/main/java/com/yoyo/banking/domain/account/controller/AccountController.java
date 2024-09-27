package com.yoyo.banking.domain.account.controller;

import com.yoyo.banking.domain.account.dto.account.AccountAuthDTO;
import com.yoyo.banking.domain.account.dto.account.AccountCreateDTO;
import com.yoyo.banking.domain.account.service.AccountService;
import com.yoyo.banking.domain.account.service.SsafyBankService;
import com.yoyo.common.dto.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yoyo/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final SsafyBankService ssafyBankService;

    /**
     *  [ssafy 금융 API] user key 생성 및 저장
     * <p>
     *  - 회원가입 시 userkey 생성 및 저장
     * */
    @PostMapping("/user-key")
    @Operation(summary = "user key 확인", description = "더미 계좌 거래 내역을 조회한다. (1원 송금 확인용)")
    ResponseEntity<?> createUserKey(@RequestHeader("memberId") Long memberId) {
        return ssafyBankService.createUserKey(memberId);
    }

    /**
     *  [ssafy 금융 API] user key 조회 및 저장
     * */
    @GetMapping("/user-key")
    @Operation(summary = "user key 확인", description = "더미 계좌 거래 내역을 조회한다. (1원 송금 확인용)")
    ResponseEntity<?> getUserKey(@RequestHeader("memberId") Long memberId) {
        return ssafyBankService.getUserKey(memberId);
    }

    /**
     *  [ssafy 금융 API] 1원 송금
     * */
    @PostMapping("/open")
    @Operation(summary = "계좌 1원 송금", description = "입력 계좌에 1원 송금한다.")
    ResponseEntity<?> openAccountAuth(@RequestHeader("memberId") Long memberId,
                                      @RequestBody @Valid AccountAuthDTO.Request request) {
        return ssafyBankService.openAccountAuth(request, memberId);
    }

    /**
     *  [ssafy 금융 API] 1원 송금 확인
     * */
    @PostMapping("/check")
    @Operation(summary = "계좌 1원 송금 확인", description = "1원 송금 코드를 확인한다.")
    ResponseEntity<?> checkAccountAuth(@RequestHeader("memberId") Long memberId,
                                       @RequestBody @Valid AccountAuthDTO.Request request) {
        return ssafyBankService.checkAccountAuth(request, memberId);
    }

    /**
    * * 계좌 등록(수정) + 페이 생성
    * <p>
     * - 계좌 있을 시, 계좌 수정
     * - 계좌 없을 시, 계좌 등록
     * @param request 계좌생성요청 DTO
    * */
    @PostMapping
    @Operation(summary = "계좌 등록, 수정", description = "계좌를 등록(수정)한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "계좌 등록(수정) 성공"),
            @ApiResponse(responseCode = "400", description = "요청 dto 필드값 오류")
    })
    ResponseEntity<CommonResponse> createAccount(@RequestHeader("memberId") Long memberId,
                                                 @RequestBody @Valid AccountCreateDTO.Request request) {
//        log.info("----------------계좌 생성------------------");
        CommonResponse response = accountService.createAccount(request, memberId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * * TODO : 계좌 삭제
     * */
}
