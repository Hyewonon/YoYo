package com.yoyo.transaction.domain;

import com.yoyo.transaction.adapter.out.persistence.TransactionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Transaction {
    private final Long transactionId;
    private final Long senderId;
    private final String senderName;
    private final Long receiverId;
    private final String receiverName;
    private final Long eventId;
    private final String title;
    private final boolean isMember;
    private final long amount;
    private final String memo;
    private final TransactionType TransactionType;

    public static Transaction generateTransaction(TransactionId transactionId,
                                                  TransactionSenderId transactionSenderId,
                                                  TransactionSenderName transactionSenderName,
                                                  TransactionReceiverId transactionReceiverId,
                                                  TransactionReceiverName transactionReceiverName,
                                                  TransactionEventId transactionEventId,
                                                  TransactionTitle transactionTitle,
                                                  TransactionIsMember transactionIsMember,
                                                  TransactionAmount transactionAmount,
                                                  TransactionMemo transactionMemo,
                                                  TransactionType transactionType) {
        return new Transaction(transactionId.transactionId,
                transactionSenderId.memberId,
                transactionSenderName.senderName,
                transactionReceiverId.memberId,
                transactionReceiverName.receiverName,
                transactionEventId.eventId,
                transactionTitle.title,
                transactionIsMember.isMember,
                transactionAmount.amount,
                transactionMemo.memo,
                transactionType
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
    public static class TransactionSenderName {
        String senderName;
        public TransactionSenderName(String value) {
            this.senderName = value;
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
    public static class TransactionReceiverName {
        String receiverName;
        public TransactionReceiverName(String value) {
            this.receiverName = value;
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
        long amount;

        public TransactionAmount(long value) {
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
