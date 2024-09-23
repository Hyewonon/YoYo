package com.yoyo.transaction.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@Builder
@Table(name = "transaction")
@AllArgsConstructor
public class TransactionJpaEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Long eventId;
    private String title;
    private boolean isMember;
    private long amount;
    private String memo;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    public TransactionJpaEntity(String senderName, Long receiverId, String receiverName, Long eventId, String title, long amount, String memo){
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.eventId = eventId;
        this.title = title;
        this.isMember = false;
        this.amount = amount;
        this.memo = memo;
    }

    public TransactionJpaEntity(Long senderId,String senderName, Long receiverId, String receiverName, Long eventId, String title,boolean isMember, long amount, String memo, TransactionType transactionType){
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.eventId = eventId;
        this.title = title;
        this.isMember = false;
        this.amount = amount;
        this.memo = memo;
        this.transactionType = transactionType;
    }
}
