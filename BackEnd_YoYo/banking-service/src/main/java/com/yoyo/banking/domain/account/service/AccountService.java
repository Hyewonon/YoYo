package com.yoyo.banking.domain.account.service;

import com.yoyo.banking.domain.account.dto.account.AccountCreateDTO;
import com.yoyo.banking.domain.account.dto.account.AccountCreateDTO.Request;
import com.yoyo.banking.domain.account.dto.account.AccountPinDTO;
import com.yoyo.banking.domain.account.repository.AccountRepository;
import com.yoyo.banking.entity.Account;
import com.yoyo.banking.global.util.BankingUtil;
import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.BankingException;
import com.yoyo.common.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final BankingUtil bankingUtil;

    /**
     * * 계좌 생성
     */
    public CommonResponse createAccount(AccountCreateDTO.Request request, Long memberId) {

        String bankCode = bankingUtil.findBankCodeByBankName(request.getBankName());
        Account account = accountRepository.findByMemberId(memberId)
                                           .map((existingAccount) -> updateAccount(existingAccount,
                                                                                   request.getAccountNumber(),
                                                                                   bankCode))
                                           .orElse(createNewAccount(request, memberId, bankCode));
        accountRepository.save(account);
        return CommonResponse.of(true, "계좌가 등록되었습니다.");
    }

    /**
     * 계좌 결제 비밀번호 (PIN 번호) 수정
     * */
    public ResponseEntity<?> updateAccountPin(AccountPinDTO.Request request, Long memberId) {
        Account account = findValidAccountByMemberId(memberId);
        String hashedPin = BCrypt.hashpw(request.getPin(), BCrypt.gensalt());
        account.setPin(hashedPin);

        accountRepository.save(account);
        ApiResponse<CommonResponse> res = new ApiResponse<>(
                HttpStatus.OK.value(),
                "PIN 수정 성공",
                CommonResponse.of(true, "PIN 수정 성공")
        );
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    /**
     * 계좌 결제 비밀번호 (PIN 번호) 확인
     * */
    public ResponseEntity<?> checkAccountPin(AccountPinDTO.Request request, Long memberId) {
        Account account = findValidAccountByMemberId(memberId);
        if (!BCrypt.checkpw(request.getPin(), account.getPin())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.of(false,"PIN 번호가 일치하지 않습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(true,"PIN 번호가 일치합니다."));
    }

    /*
    * * 새로운 계좌 생성
    * */
    private Account createNewAccount(Request request, Long memberId, String bankCode) {
        return AccountCreateDTO.Request.toEntity(request, memberId, bankCode);
    }

    /*
    * * 기존 계좌 계좌번호, 은행명 수정
    * */
    private Account updateAccount(Account account, String accountNumber, String bankCode) {
        account.setAccountNumber(accountNumber);
        account.setBankCode(bankCode);
        return account;
    }

    /*
     * 등록된 계좌인지 확인
     * */
    private Account findValidAccountByMemberId(Long memberId) {
        Account account = accountRepository.findByMemberId(memberId)
                                           .orElseThrow(() -> new BankingException(ErrorCode.NOT_FOUND_MEMBER));

        if(account.getAccountNumber() == null) throw new BankingException(ErrorCode.NOT_FOUND_ACCOUNT);
        return account;
    }
}
