package com.yoyo.banking.domain.account.service;

import com.yoyo.banking.domain.account.dto.account.AccountCreateDTO;
import com.yoyo.banking.domain.account.dto.account.AccountCreateDTO.Request;
import com.yoyo.banking.domain.account.repository.AccountRepository;
import com.yoyo.banking.entity.Account;
import com.yoyo.banking.global.util.BankingUtil;
import com.yoyo.common.dto.response.CommonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
