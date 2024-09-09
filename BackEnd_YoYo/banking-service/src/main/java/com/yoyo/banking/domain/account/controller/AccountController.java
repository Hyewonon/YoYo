package com.yoyo.banking.domain.account.controller;

import com.yoyo.banking.domain.account.dto.AccountCreateDTO;
import com.yoyo.banking.domain.account.service.AccountService;
import com.yoyo.common.dto.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/yoyo/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    //임시 멤버
    private Long memberId = 1L;

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
    ResponseEntity<CommonResponse> createAccount(@RequestBody @Valid AccountCreateDTO.Request request,
                                                 Long memberId) {
        log.info("----------------계좌 생성------------------");
        Long currentMemberId = memberId;
        CommonResponse response = accountService.createAccount(request, currentMemberId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
