package com.yoyo.banking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pay_transaction")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payTransactionId;

    private Long accountId;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    private Long payAmount;

    private LocalDateTime createdAt;

    private String name;
}
