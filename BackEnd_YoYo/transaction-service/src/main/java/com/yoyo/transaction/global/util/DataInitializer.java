package com.yoyo.transaction.global.util;

import com.yoyo.transaction.domain.transaction.repository.TransactionRepository;
import com.yoyo.transaction.entity.RelationType;
import com.yoyo.transaction.entity.Transaction;
import com.yoyo.transaction.entity.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final TransactionRepository transactionRepository;

    public static List<Transaction> generateDummyTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = Transaction.builder()
                .senderId(1L)
                .senderName("최광림")
                .receiverId(2L)
                .receiverName("김현태")
                .eventId(1L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(50000L)
                .memo("최광림 -> 김현태")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();
        Transaction transaction2 = Transaction.builder()
                .senderId(2L)
                .senderName("김현태")
                .receiverId(3L)
                .receiverName("이찬진")
                .eventId(2L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(50000L)
                .memo("김현태 -> 이찬진")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();
        Transaction transaction3 = Transaction.builder()
                .senderId(3L)
                .senderName("이찬진")
                .receiverId(2L)
                .receiverName("김현태")
                .eventId(1L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(50000L)
                .memo("이찬진 -> 김현태")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();
        Transaction transaction4 = Transaction.builder()
                .senderId(2L)
                .senderName("김현태")
                .receiverId(1L)
                .receiverName("최광림")
                .eventId(3L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(50000L)
                .memo("김현태 -> 최광림")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();
        Transaction transaction5 = Transaction.builder()
                .senderId(3L)
                .senderName("이찬진")
                .receiverId(1L)
                .receiverName("최광림")
                .eventId(3L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(50000L)
                .memo("이찬진 -> 최광림")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();
        Transaction transaction6 = Transaction.builder()
                .senderId(1L)
                .senderName("최광림")
                .receiverId(3L)
                .receiverName("이찬진")
                .eventId(4L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(70000L)
                .memo("최광림 -> 이찬진")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();

        Transaction transaction7 = Transaction.builder()
                .senderId(3L)
                .senderName("이찬진")
                .receiverId(1L)
                .receiverName("최광림")
                .eventId(4L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(50000L)
                .memo("이찬진 -> 최광림")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();

        Transaction transaction8 = Transaction.builder()
                .senderId(1L)
                .senderName("최광림")
                .receiverId(2L)
                .receiverName("김현태")
                .eventId(2L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(40000L)
                .memo("최광림 -> 김현태")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();

        Transaction transaction9 = Transaction.builder()
                .senderId(2L)
                .senderName("김현태")
                .receiverId(1L)
                .receiverName("최광림")
                .eventId(2L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(45000L)
                .memo("김현태 -> 최광림")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();

        Transaction transaction10 = Transaction.builder()
                .senderId(1L)
                .senderName("최광림")
                .receiverId(3L)
                .receiverName("이찬진")
                .eventId(3L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(60000L)
                .memo("최광림 -> 이찬진")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();

        Transaction transaction11 = Transaction.builder()
                .senderId(3L)
                .senderName("이찬진")
                .receiverId(1L)
                .receiverName("최광림")
                .eventId(3L)
                .eventName("결혼식")
                .isRegister(true)
                .amount(65000L)
                .memo("이찬진 -> 최광림")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.FRIEND)
                .build();
        Transaction transaction12 = Transaction.builder()
                .senderId(6L)
                .senderName("김싸피")
                .receiverId(2L)
                .receiverName("김현태")
                .eventId(2L)
                .eventName("결혼식")
                .isRegister(false)
                .amount(2000L)
                .memo("축하합니다.")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.NONE)
                .build();
        Transaction transaction13 = Transaction.builder()
                .senderId(7L)
                .senderName("이택근")
                .receiverId(2L)
                .receiverName("김현태")
                .eventId(2L)
                .eventName("결혼식")
                .isRegister(false)
                .amount(50000L)
                .memo("오랜만이다")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.NONE)
                .build();
        Transaction transaction14 = Transaction.builder()
                .senderId(6L)
                .senderName("김싸피")
                .receiverId(3L)
                .receiverName("이찬진")
                .eventId(3L)
                .eventName("결혼식")
                .isRegister(false)
                .amount(2000L)
                .memo("츅하요")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.NONE)
                .build();
        Transaction transaction15 = Transaction.builder()
                .senderId(7L)
                .senderName("이택근")
                .receiverId(3L)
                .receiverName("이찬진")
                .eventId(3L)
                .eventName("결혼식")
                .isRegister(false)
                .amount(50000L)
                .memo("오랜만이다")
                .transactionType(TransactionType.AUTO)
                .relationType(RelationType.NONE)
                .build();
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        transactions.add(transaction5);
        transactions.add(transaction6);
        transactions.add(transaction7);
        transactions.add(transaction8);
        transactions.add(transaction9);
        transactions.add(transaction10);
        transactions.add(transaction11);
        transactions.add(transaction12);
        transactions.add(transaction13);
        transactions.add(transaction14);
        transactions.add(transaction15);
        return transactions;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (transactionRepository.count() == 0) {
        List<Transaction> dummyTransactions = generateDummyTransactions();
        transactionRepository.saveAll(dummyTransactions);
        }
    }
}
