package com.yoyo.transaction.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@Builder
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
    private int amount;
    private String memo;

    public TransactionJpaEntity(String senderName, Long receiverId, String receiverName, Long eventId, String title, int amount, String memo){
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.eventId = eventId;
        this.title = title;
        this.isMember = false;
        this.amount = amount;
        this.memo = memo;
    }
}
