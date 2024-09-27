package com.yoyo.transaction.domain.transaction.repository;

import com.yoyo.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.receiverId = :receiverId AND t.eventId = :eventId")
    List<Transaction> findByReceiverIdAndEventId(@Param("receiverId") Long receiverId, @Param("eventId") Long eventId);

    List<Transaction> findByEventIdAndReceiverId(Long eventId, Long receiverId);

    @Query("SELECT t FROM Transaction  t WHERE t.receiverId = :receiverId AND t.eventId = :eventId AND (:search IS NULL OR t.senderName LIKE %:search%)" +
            "AND (:isRegister IS NULL OR t.isRegister = :isRegister)")
    List<Transaction> findTransactionEvent(@Param("receiverId") Long receiverId, @Param("eventId") Long eventId, @Param("search") String search, @Param("isRegister") Boolean isRegister);
}
