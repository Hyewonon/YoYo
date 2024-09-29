package com.yoyo.banking.global.util;

import com.yoyo.banking.domain.account.repository.AccountRepository;
import com.yoyo.banking.domain.account.repository.BankRepository;
import com.yoyo.banking.entity.Account;
import com.yoyo.banking.entity.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;


    public static List<Account> generateDummyAccounts() {
        List<Account> accounts = new ArrayList<>();

        Account account1 = Account.builder()
                .memberId(1L)
                .accountNumber("9991952997044858")
                .bankCode("011")
                .balance(0L)
                .pin("1234")
                .userKey("ca38f7e9-c736-44cf-96aa-c152d040d50a")
                .build();

        Account account2 = Account.builder()
                .memberId(2L)
                .accountNumber("9996441146967187")
                .bankCode("004")
                .balance(0L)
                .pin("002")
                .userKey("cb1619b1-daf2-43e3-9e44-ed64f9ef212b")
                .build();
        Account account3 = Account.builder()
                .memberId(3L)
                .accountNumber("9990010761414577")
                .bankCode("003")
                .balance(0L)
                .pin("003")
                .userKey("0b2cd3ff-a284-4459-b399-c0abc0ff349c")
                .build();
        Account account4 = Account.builder()
                .memberId(4L)
                .accountNumber("9997693456333174")
                .bankCode("999")
                .balance(0L)
                .pin("004")
                .userKey("3ebc5f5e-3a92-4802-9d4b-30574aac23c5")
                .build();
        Account account5 = Account.builder()
                .memberId(5L)
                .accountNumber("9992403965057438")
                .bankCode("999")
                .balance(0L)
                .pin("1234")
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
        Set<Bank> banks = new HashSet<>();

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
        List<Account> dummyAccounts = generateDummyAccounts();
        accountRepository.saveAll(dummyAccounts);
        }
    }
}
