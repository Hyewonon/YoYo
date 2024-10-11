package com.yoyo.banking.global.util;

import com.yoyo.banking.domain.account.dto.account.AccountCreateDTO;
import com.yoyo.banking.domain.account.repository.AccountRepository;
import com.yoyo.banking.domain.account.repository.BankRepository;
import com.yoyo.banking.domain.account.service.AccountService;
import com.yoyo.banking.domain.account.service.PayService;
import com.yoyo.banking.entity.Account;
import com.yoyo.banking.entity.Bank;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final AesService aesService;
    private final PayService payService;

    private final AccountService accountService;
    private final String hashedPin = BCrypt.hashpw("111111", BCrypt.gensalt());
    private final Map<Long, String> realAccounts = real_account_생성();
    private final Set<Bank> banks = new HashSet<>();

    public void user_key_저장() {
        for (long memberId = 1; memberId <= 35; memberId++) {
            payService.createUserKey(memberId);
        }
    }

    public AccountCreateDTO.Request 계좌_등록_dto_생성(String accountNumber, String bankName) {
        return AccountCreateDTO.Request.builder()
                .accountNumber(accountNumber)
                .bankName(bankName)
                .isAuthenticated(true)
                .pin(hashedPin)
                .build();
    }
    public String getRandomBankName() {
        List<String> validBankNames = new ArrayList<>();

        for (Bank bank : banks) {
            if (!bank.getBankName().equals("한국은행") && !bank.getBankName().equals("싸피은행")) {
                validBankNames.add(bank.getBankName());
            }
        }
        Random random = new Random();
        return validBankNames.get(random.nextInt(validBankNames.size()));
    }
    public Map<Long, String> real_account_생성(){
        Map<Long, String> realAccounts = new HashMap<>();
        realAccounts.put(1L, "9991952997044858");
        realAccounts.put(2L, "9996441146967187");
        realAccounts.put(3L, "9990010761414577");
        realAccounts.put(4L, "9997693456333174");
        realAccounts.put(5L, "9992403965057438");
        realAccounts.put(6L, "9999246626725136");
        realAccounts.put(7L, "9990390432343079");
        realAccounts.put(8L, "9995012558804030");
        realAccounts.put(9L, "9994698608163306");
        realAccounts.put(10L, "9997767801782078");

        realAccounts.put(11L, "9997672059819310");
        realAccounts.put(12L, "9998419655837863");
        realAccounts.put(13L, "9991042926156374");
        realAccounts.put(14L, "9994354416248418");
        realAccounts.put(15L, "9995846466474461");
        realAccounts.put(16L, "9999215592836725");
        realAccounts.put(17L, "9993137942987204");
        realAccounts.put(18L, "9997640789664011");
        realAccounts.put(19L, "9992205685933853");
        realAccounts.put(20L, "9993168876949485");

        realAccounts.put(21L, "9994915793301083");
        realAccounts.put(22L, "9992106586730812");
        realAccounts.put(23L, "9998235141457007");
        realAccounts.put(24L, "9995940300524328");
        realAccounts.put(25L, "9997089411604462");
        realAccounts.put(26L, "9994755783925422");
        realAccounts.put(27L, "9998084964477128");
        realAccounts.put(28L, "9992529890584949");
        realAccounts.put(29L, "9994380823348235");
        realAccounts.put(30L, "9995477151708315");

        realAccounts.put(31L, "9993125242606461");
        realAccounts.put(32L, "9996519663212761");
        realAccounts.put(33L, "9999720309482505");
        realAccounts.put(34L, "9999861157235959");
        realAccounts.put(35L, "9993678855931950");
        return realAccounts;
    }

    public void dummy_account_생성(){
        for (long memberId = 1; memberId <=35; memberId++) {
            String accountNumber = realAccounts.get(memberId);
            String bankName = getRandomBankName();
            AccountCreateDTO.Request request = 계좌_등록_dto_생성(accountNumber, bankName);
            accountService.createAccount(request, memberId);
        }
    }

    public List<Account> generateDummyAccounts() {
        List<Account> accounts = new ArrayList<>();

        Account account1 = Account.builder()
                .memberId(1L)
                .accountNumber(aesService.encrypt("9991952997044858"))
                .bankCode("011")
                .balance(0L)
                .pin(hashedPin)
                .userKey("ca38f7e9-c736-44cf-96aa-c152d040d50a")
                .build();

        Account account2 = Account.builder()
                .memberId(2L)
                .accountNumber(aesService.encrypt("9996441146967187"))
                .bankCode("004")
                .balance(0L)
                .pin(hashedPin)
                .userKey("cb1619b1-daf2-43e3-9e44-ed64f9ef212b")
                .build();
        Account account3 = Account.builder()
                .memberId(3L)
                .accountNumber(aesService.encrypt("9990010761414577"))
                .bankCode("003")
                .balance(0L)
                .pin(hashedPin)
                .userKey("0b2cd3ff-a284-4459-b399-c0abc0ff349c")
                .build();
        Account account4 = Account.builder()
                .memberId(4L)
                .accountNumber(aesService.encrypt("9997693456333174"))
                .bankCode("999")
                .balance(0L)
                .pin(hashedPin)
                .userKey("3ebc5f5e-3a92-4802-9d4b-30574aac23c5")
                .build();
        Account account5 = Account.builder()
                .memberId(5L)
                .accountNumber(aesService.encrypt("9992403965057438"))
                .bankCode("999")
                .balance(0L)
                .pin(hashedPin)
                .userKey("b272b182-e7a1-4956-80d6-1093371e550c")
                .build();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account4);
        accounts.add(account5);

        return accounts;
    }
    public void generateBanking() {
        banks.add(Bank.builder().bankCode("001").bankName("한국은행").build());
        banks.add(Bank.builder().bankCode("002").bankName("산업은행").build());
        banks.add(Bank.builder().bankCode("003").bankName("IBK기업은행").build());
        banks.add(Bank.builder().bankCode("004").bankName("KB국민은행").build());
        banks.add(Bank.builder().bankCode("011").bankName("NH농협은행").build());
        banks.add(Bank.builder().bankCode("020").bankName("우리은행").build());
        banks.add(Bank.builder().bankCode("023").bankName("SC제일은행").build());
        banks.add(Bank.builder().bankCode("027").bankName("시티은행").build());
        banks.add(Bank.builder().bankCode("032").bankName("대구은행").build());
        banks.add(Bank.builder().bankCode("034").bankName("광주은행").build());
        banks.add(Bank.builder().bankCode("035").bankName("제주은행").build());
        banks.add(Bank.builder().bankCode("037").bankName("전북은행").build());
        banks.add(Bank.builder().bankCode("039").bankName("경남은행").build());
        banks.add(Bank.builder().bankCode("045").bankName("새마을금고").build());
        banks.add(Bank.builder().bankCode("081").bankName("하나은행").build());
        banks.add(Bank.builder().bankCode("088").bankName("신한은행").build());
        banks.add(Bank.builder().bankCode("090").bankName("카카오뱅크").build());
        banks.add(Bank.builder().bankCode("091").bankName("토스뱅크").build());
        banks.add(Bank.builder().bankCode("999").bankName("싸피은행").build());
        bankRepository.saveAll(banks);
    }

    @Override
    public void run(String... args) throws Exception {
        if (bankRepository.count() == 0) {
            generateBanking();
        }
        if (accountRepository.count() == 0) {
//            List<Account> dummyAccounts = generateDummyAccounts();
//            accountRepository.saveAll(dummyAccounts);

            // ---------위 코드 주석 처리 후 아래 코드 주석 해제 : 35명 생성-------
            user_key_저장();
            dummy_account_생성();
        }
    }
}
