package com.yoyo.banking.domain.account.repository;

import com.yoyo.banking.entity.PayTransaction;
import com.yoyo.banking.entity.PayType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PayTransactionRepository extends JpaRepository<PayTransaction, Long> {

    /**
     * 입급 / 출금 페이 거래 내역 조회 (최신순)
     * - 입금 내역 조회  : PayType DEPOSIT, CHARGE
     * - 출금 내역 조회 : PayType WITHDRAW, REFUND
     */
    @Query("SELECT pt FROM PayTransaction pt WHERE pt.accountId = :accountId AND pt.payType IN :payTypes ORDER BY pt.createdAt DESC")
    List<PayTransaction> findByAccountIdAndPayType(@Param("accountId") Long accountId, @Param("payTypes") List<PayType> payTypes);

}
