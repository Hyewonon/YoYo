package com.yoyo.banking.domain.account.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.yoyo.banking.entity.Bank;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class BankRepositoryTest {

    @Autowired
    private BankRepository bankRepository;

    private final static String TEST_CONTAINER_IMAGE_TAG = "mysql:8.0";

    static MySQLContainer<?> mysql = new MySQLContainer<>(TEST_CONTAINER_IMAGE_TAG)
            .withDatabaseName("yoyo");

    @BeforeAll
    static void beforeAll() {
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.database", mysql::getDatabaseName);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");

    }
    @Test
    void 은행_데이터_생성() {
        Set<Bank> banks = new HashSet<>();
        Bank bank1 = Bank.builder()
                         .bankCode("001")
                         .bankName("한국은행")
                         .build();
        banks.add(bank1);

        Bank bank2 = Bank.builder()
                         .bankCode("002")
                         .bankName("산업은행")
                         .build();
        banks.add(bank2);

        Bank bank3 = Bank.builder()
                         .bankCode("003")
                         .bankName("IBK기업은행")
                         .build();
        banks.add(bank3);
        Bank bank4 = Bank.builder()
                         .bankCode("004")
                         .bankName("KB국민은행")
                         .build();
        banks.add(bank4);

        Bank bank5 = Bank.builder()
                         .bankCode("011")
                         .bankName("NH농협은행")
                         .build();
        banks.add(bank5);

        Bank bank6 = Bank.builder()
                         .bankCode("020")
                         .bankName("우리은행")
                         .build();
        banks.add(bank6);

        Bank bank7 = Bank.builder()
                         .bankCode("023")
                         .bankName("SC제일은행")
                         .build();
        banks.add(bank7);
        Bank bank8 = Bank.builder()
                         .bankCode("027")
                         .bankName("시티은행")
                         .build();
        banks.add(bank8);

        Bank bank9 = Bank.builder()
                         .bankCode("032")
                         .bankName("대구은행")
                         .build();
        banks.add(bank9);

        Bank bank10 = Bank.builder()
                          .bankCode("034")
                          .bankName("광주은행")
                          .build();
        banks.add(bank10);

        Bank bank11 = Bank.builder()
                          .bankCode("035")
                          .bankName("제주은행")
                          .build();
        banks.add(bank11);

        Bank bank12 = Bank.builder()
                          .bankCode("037")
                          .bankName("전북은행")
                          .build();
        banks.add(bank12);

        Bank bank13 = Bank.builder()
                          .bankCode("039")
                          .bankName("경남은행")
                          .build();
        banks.add(bank13);

        Bank bank14 = Bank.builder()
                          .bankCode("045")
                          .bankName("새마을금고")
                          .build();
        banks.add(bank14);

        Bank bank15 = Bank.builder()
                          .bankCode("081")
                          .bankName("하나은행")
                          .build();
        banks.add(bank15);

        Bank bank16 = Bank.builder()
                          .bankCode("088")
                          .bankName("신한은행")
                          .build();
        banks.add(bank16);

        Bank bank17 = Bank.builder()
                          .bankCode("090")
                          .bankName("카카오뱅크")
                          .build();
        banks.add(bank17);

        Bank bank18 = Bank.builder()
                          .bankCode("091")
                          .bankName("토스뱅크")
                          .build();
        banks.add(bank18);

        Bank bank19 = Bank.builder()
                          .bankCode("999")
                          .bankName("싸피은행")
                          .build();
        banks.add(bank19);

        bankRepository.saveAll(banks);
        assertThat(bankRepository.findById(Long.valueOf(bank19.getBankCode())).isPresent());
    }
}