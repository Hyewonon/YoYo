package com.yoyo.banking.domain.account.repository;

import com.yoyo.banking.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
    /*
    * * member Id로 계좌 정보 찾음
    * */
    Optional<Account> findByMemberId(Long memberId);

    /*
    * * memberId로 userKey 찾음
    * */
    @Query("SELECT a.userKey FROM Account a WHERE a.memberId = :memberId")
    Optional<String> findUserKeyByMemberId(Long memberId);

    /*
    * * memberId로 페이 잔액 조회
     */
    @Query("SELECT a.balance FROM Account a WHERE a.memberId = :memberId")
    Long findBalanceByMemberId(Long memberId);
}
