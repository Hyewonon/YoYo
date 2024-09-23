package com.yoyo.banking.domain.account.service;

import com.yoyo.banking.domain.account.dto.PayDTO;
import com.yoyo.banking.domain.account.dto.PayTransactionDTO;
import com.yoyo.banking.domain.account.dto.PayTransferDTO;
import com.yoyo.banking.domain.account.repository.AccountRepository;
import com.yoyo.banking.domain.account.repository.PayTransactionRepository;
import com.yoyo.banking.entity.Account;
import com.yoyo.banking.entity.PayTransaction;
import com.yoyo.banking.entity.PayType;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.BankingException;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PayService {

    private final SsafyBankService ssafyBankService;
    private final AccountRepository accountRepository;
    private final PayTransactionRepository payTransactionRepository;

    /**
     * * 페이 머니 충전 / 환불
     * <p>
     */
    public ResponseEntity<?> updatePayBalance(PayDTO.Request request, Long memberId, Boolean isDeposit) {
        // 1. 페이머니 충전 (=계좌 출금 요청) /  환불 (=계좌 입금 요청)
        // 1.1 환불시 페이머니 잔액보다 큰금액이면 예외처리
        Long payBalance = accountRepository.findBalanceByMemberId(memberId);
        log.info("payBalance: {}", payBalance);
        if(isDeposit && request.getPayAmount() > payBalance) {
            log.info("! 페이 잔액보다 환불금이 더 크다");
            throw new BankingException(ErrorCode.EXCEEDS_PAY_BALANCE);
        }

        ResponseEntity<?> response = ssafyBankService.updateDemandDeposit(memberId, request.getPayAmount(), isDeposit);

        if (response.getStatusCode().is2xxSuccessful()) {
            // 1.2 페이머니 잔액 업데이트
            Account account = findAccountByMemberId(memberId);

            if(isDeposit) account.setBalance(account.getBalance() - request.getPayAmount());
            else account.setBalance(account.getBalance() + request.getPayAmount());

            // 2. 페이 거래내역 저장
            PayType payType = (isDeposit ? PayType.REFUND : PayType.CHARGE);
            savePayTransaction(request, memberId, payType);
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }
    }

    /**
    * * TODO : 페이 거래
    * */
    public ResponseEntity<?> transferPayment(PayTransferDTO.Request request, Long currMemberId) {
        // 1. 페이머니 충분한지 확인
        // 1.1 충분하지 않으면 충전
        Long insufficientAmount = getInsufficientAmount(request.getPayAmount(), currMemberId);
        if(insufficientAmount < 0) {
            PayDTO.Request chargeRequest = PayDTO.Request.toDto(insufficientAmount, null); // TODO : 회원 이름 불러오기
            ResponseEntity<?> chargeResult = updatePayBalance(chargeRequest, currMemberId, false);

            // 충전 실패했으면 응답반환
            if (!chargeResult.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(chargeResult.getStatusCode()).body(chargeResult);
            }
        }

        // 2. 페이머니 잔액 변경 ( 나, 친구)
        Account myAccount = findAccountByMemberId(currMemberId);
        myAccount.setBalance(myAccount.getBalance() - request.getPayAmount());
        Account friendAccount = findAccountByMemberId(request.getMemberId());
        friendAccount.setBalance(friendAccount.getBalance() + request.getPayAmount());

        // 3. 페이 거래내역 생성

        PayDTO.Request transferRequest = PayDTO.Request.toDto(request.getPayAmount(), request.getMemberName());
        savePayTransaction(transferRequest, currMemberId, PayType.WITHDRAW); // 내계좌 출금
        savePayTransaction(transferRequest, request.getMemberId(), PayType.DEPOSIT); // 상대계좌 입금

        // TODO 4. 친구관계 생성 및 총금액 수정


        // TODO 5. 보냈어요 받았어요 거래내역 생성

        return null;
    }

    /**
    * * 페이머니 부족한 금액 확인
    * @return <0 : 부족
    * */
    private Long getInsufficientAmount(Long payAmount, Long memberId) {
        Long balance = accountRepository.findBalanceByMemberId(memberId);
        return balance - payAmount;
    }

    /*
     * 페이 거래 내역 저장
     * */
    private void savePayTransaction(PayDTO.Request request, Long memberId, PayType payType) {
        Account account = findAccountByMemberId(memberId);
        PayTransaction payTransaction = PayDTO.Request.toEntity(request, account.getAccountId(), null,
                                                                payType); // 회원 이름 필요

        payTransactionRepository.save(payTransaction);
    }


    /**
     * * 페이 거래 내역 조회
     */
    public ResponseEntity<?> getPayTransactions(String transactionType, Long memberId) {
        Account account = findAccountByMemberId(memberId);

        // - 입금 내역 조회  : PayType DEPOSIT, CHARGE
        // - 출금 내역 조회 : PayType WITHDRAW, REFUND
        List<PayTransaction> payTransactions;
        List<PayType> payTypes = (transactionType.equals("DEPOSIT")) ? Arrays.asList(PayType.CHARGE, PayType.DEPOSIT)
                                                                     : Arrays.asList(PayType.REFUND, PayType.WITHDRAW);

        payTransactions = payTransactionRepository.findByAccountIdAndPayType(account.getAccountId(), payTypes);

        List<PayTransactionDTO.Response> responses = payTransactions.stream()
                                                                    .map(PayTransactionDTO.Response::of)
                                                                    .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    /**
     * * 페이 잔액 조회
     */
    public ResponseEntity<?> getPayBalance(Long memberId) {
        Account account = findAccountByMemberId(memberId);
        return ResponseEntity.ok(PayDTO.Response.of(account));
    }

    private Account findAccountByMemberId(Long memberId) {
        return accountRepository.findByMemberId(memberId)
                                .orElseThrow(() -> new BankingException(ErrorCode.NOT_FOUND_ACCOUNT));
    }
}
