package com.yoyo.banking.domain.account.service;

import com.yoyo.banking.domain.account.dto.account.AccountCreateDTO;
import com.yoyo.banking.domain.account.dto.account.AccountCreateDTO.Request;
import com.yoyo.banking.domain.account.dto.account.AccountPinDTO;
import com.yoyo.banking.domain.account.repository.AccountRepository;
import com.yoyo.banking.entity.Account;
import com.yoyo.banking.global.util.AesService;
import com.yoyo.banking.global.util.BankingUtil;
import com.yoyo.common.dto.response.CommonResponse;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.BankingException;
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
    private final AesService aesService;

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

        String hashedPin = BCrypt.hashpw(request.getPin(), BCrypt.gensalt());
        account.setPin(hashedPin);

        accountRepository.save(account);
        return CommonResponse.of(true, "계좌가 등록되었습니다.");
    }

    /**
     * * 등록된 계좌 조회
     */
    public ResponseEntity<AccountCreateDTO.Response> getAccount(Long memberId) {
        Account account = findAccountByMemberId(memberId);
        checkAccountRegister(account);
        String bankName = bankingUtil.findBankNameByBankCode(account.getBankCode());
        String decryptAccountNum = aesService.decrypt(account.getAccountNumber());
        AccountCreateDTO.Response response = AccountCreateDTO.Response.of(decryptAccountNum, bankName);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 계좌 결제 비밀번호 (PIN 번호) 수정
     * */
    public ResponseEntity<?> updateAccountPin(AccountPinDTO.Request request, Long memberId) {
        Account account = findAccountByMemberId(memberId);
        checkAccountRegister(account);
        String hashedPin = BCrypt.hashpw(request.getPin(), BCrypt.gensalt());
        account.setPin(hashedPin);

        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(true, "PIN 번호 수정 성공"));
    }


    /**
     * 계좌 결제 비밀번호 (PIN 번호) 확인
     * */
    public ResponseEntity<?> checkAccountPin(AccountPinDTO.Request request, Long memberId) {
        Account account = findAccountByMemberId(memberId);

        if (!BCrypt.checkpw(request.getPin(), account.getPin())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.of(false,"PIN 번호가 일치하지 않습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.of(true,"PIN 번호가 일치합니다."));
    }

    /*
    * * 새로운 계좌 생성
    * */
    private Account createNewAccount(Request request, Long memberId, String bankCode) {
        String encryptAccountNum = aesService.encrypt(request.getAccountNumber());
        return AccountCreateDTO.Request.toEntity(request, memberId, bankCode, encryptAccountNum);
    }

    /*
    * * 기존 계좌 계좌번호, 은행명 수정
    * */
    private Account updateAccount(Account account, String accountNumber, String bankCode) {
        account.setAccountNumber(aesService.encrypt(accountNumber));
        account.setBankCode(bankCode);
        return account;
    }

    /*
     * 회원 id로 account 객체가 생성됐는지 확인
     * */
    private Account findAccountByMemberId(Long memberId) {
        return accountRepository.findByMemberId(memberId)
                                .orElseThrow(() -> new BankingException(ErrorCode.NOT_FOUND_MEMBER));
    }
    /*
     * 등록된 계좌인지 확인
     * */
    private void checkAccountRegister(Account account) {
        if(account.getAccountNumber() == null) throw new BankingException(ErrorCode.NOT_FOUND_ACCOUNT);
    }
}
