package com.yoyo.transaction.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@Builder
@Table(name = "transaction")
@AllArgsConstructor
public class Transaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Long eventId;
    private String eventName;
    private Boolean isRegister;
    private Long amount;
    private String memo;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public Transaction(String senderName, Long receiverId, String receiverName, Long eventId, String title, long amount, String memo){
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.eventId = eventId;
        this.eventName = title;
        this.isRegister = false;
        this.amount = amount;
        this.memo = memo;
        this.createdAt = LocalDateTime.now();
    }

    public Transaction(Long senderId, String senderName, Long receiverId, String receiverName, Long eventId, String title, boolean isRegister, long amount, String memo, TransactionType transactionType){
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.eventId = eventId;
        this.eventName = title;
        this.isRegister = false;
        this.amount = amount;
        this.memo = memo;
        this.transactionType = transactionType;
        this.createdAt = LocalDateTime.now();
    }
}
