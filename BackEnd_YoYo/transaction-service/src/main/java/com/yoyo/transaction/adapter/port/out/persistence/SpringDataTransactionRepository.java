package com.yoyo.transaction.adapter.port.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTransactionRepository extends JpaRepository<TransactionJpaEntity, Long> {

}
