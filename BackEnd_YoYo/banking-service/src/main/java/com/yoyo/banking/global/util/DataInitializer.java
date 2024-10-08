package com.yoyo.banking.global.util;

import com.yoyo.banking.domain.account.repository.AccountRepository;
import com.yoyo.banking.domain.account.repository.BankRepository;
import com.yoyo.banking.entity.Account;
import com.yoyo.banking.entity.Bank;
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
    private final BankRepository bankRepository;

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
    }
}
