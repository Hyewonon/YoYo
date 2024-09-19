package com.yoyo.transaction.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Transaction {
    private final Long transactionId;
    private final Long senderId;
    private final Long receiverId;
    private final Long eventId;
    private final String title;
    private final boolean isMember;
    private final Long amount;
    private final String memo;

    public static Transaction generateTransaction(TransactionId transactionId,
                                                  TransactionSenderId transactionSenderId,
                                                  TransactionReceiverId transactionReceiverId,
                                                  TransactionEventId transactionEventId,
                                                  TransactionTitle transactionTitle,
                                                  TransactionIsMember transactionIsMember,
                                                  TransactionAmount transactionAmount,
                                                  TransactionMemo transactionMemo) {
        return new Transaction(transactionId.transactionId,
                transactionSenderId.memberId,
                transactionReceiverId.memberId,
                transactionEventId.eventId,
                transactionTitle.title,
                transactionIsMember.isMember,
                transactionAmount.amount,
                transactionMemo.memo
        );
    }

    @Value
    public static class TransactionId {
        Long transactionId;

        public TransactionId(Long value) {
            this.transactionId = value;
        }
    }

    @Value
    public static class TransactionSenderId {
        Long memberId;

        public TransactionSenderId(Long value) {
            this.memberId = value;
        }
    }
    @Value
    public static class TransactionReceiverId {
        Long memberId;

        public TransactionReceiverId(Long value) {
            this.memberId = value;
        }
    }

    @Value
    public static class TransactionEventId {
        Long eventId;

        public TransactionEventId(Long value) {
            this.eventId = value;
        }
    }

    @Value
    public static class TransactionTitle {
        String title;

        public TransactionTitle(String value) {
            this.title = value;
        }
    }

    @Value
    public static class TransactionIsMember {
        boolean isMember;

        public TransactionIsMember(boolean value) {
            this.isMember = value;
        }
    }

    @Value
    public static class TransactionAmount {
        Long amount;

        public TransactionAmount(Long value) {
            this.amount = value;
        }
    }

    @Value
    public static class TransactionMemo {
        String memo;

        public TransactionMemo(String value) {
            this.memo = value;
        }
    }
}
