package com.yoyo.banking.domain.account.repository;

import com.yoyo.banking.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    /*
    * * member Id로 계좌 정보 찾음
    * */
    Optional<Account> findByMemberId(Long memberId);
}
