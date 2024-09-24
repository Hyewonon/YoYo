package com.yoyo.transaction.application.port.in;

import com.yoyo.common.annotation.SelfValidating;
import com.yoyo.transaction.adapter.out.persistence.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterTransactionCommand extends SelfValidating<RegisterTransactionCommand> {
    private Long senderId;
    private String senderName;
    // 받았어요 -> 내가 받은 사람
    private Long receiverId;
    private String receiverName;
    private Long relationId;
    private Long eventId;
    private String eventName;
    @NotBlank
    @NotNull
    private long amount;
    private String memo;
    private TransactionType transactionType;
}
