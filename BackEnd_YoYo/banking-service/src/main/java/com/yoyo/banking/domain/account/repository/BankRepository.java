package com.yoyo.banking.domain.account.repository;

import com.yoyo.banking.entity.Bank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankRepository extends JpaRepository<Bank, Long> {
    /*
    * * '은행명'으로 '기관코드'를 찾음
    * */
    @Query("SELECT b.bankCode FROM Bank b WHERE b.bankName = :bankName")
    Optional<Long> findBankCodeByBankName(String bankName);
}
