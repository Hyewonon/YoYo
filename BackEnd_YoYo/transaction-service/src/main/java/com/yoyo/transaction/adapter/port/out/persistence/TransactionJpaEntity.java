package com.yoyo.transaction.adapter.port.out.persistence;

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
    private Long receiverId;
    private Long eventId;
    private String title;
    private boolean isMember;
    private Long amount;
    private String memo;
}
