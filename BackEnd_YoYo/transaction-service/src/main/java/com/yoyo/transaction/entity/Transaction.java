package com.yoyo.transaction.entity;

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
public class Transaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    private Long senderId;
    @Setter
    private String senderName;
    private Long receiverId;
    @Setter
    private String receiverName;
    private Long eventId;
    @Setter
    private String eventName;
    @Setter
    private Boolean isRegister;
    @Setter
    private long amount;
    @Setter
    private String memo;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Setter
    @Enumerated(EnumType.STRING)
    private RelationType relationType;
}
