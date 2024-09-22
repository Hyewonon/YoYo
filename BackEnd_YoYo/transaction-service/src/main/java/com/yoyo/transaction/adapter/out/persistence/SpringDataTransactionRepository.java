package com.yoyo.transaction.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataTransactionRepository extends JpaRepository<TransactionJpaEntity, Long> {
    @Query("SELECT t FROM TransactionJpaEntity t WHERE t.receiverId = :receiverId AND t.eventId = :eventId")
    List<TransactionJpaEntity> findByReceiverIdAndEventId(@Param("receiverId") Long receiverId,@Param("eventId") Long eventId);

    List<TransactionJpaEntity> findAllBySenderId(Long senderId);
    List<TransactionJpaEntity> findAllByReceiverId(Long receiverId);
}
