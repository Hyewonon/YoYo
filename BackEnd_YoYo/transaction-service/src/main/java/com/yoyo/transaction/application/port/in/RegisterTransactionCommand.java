package com.yoyo.transaction.application.port.in;

import com.yoyo.common.annotation.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RegisterTransactionCommand extends SelfValidating<RegisterTransactionCommand> {

    private  Long senderId;
    private  String senderName;
    private  Long receiverId;
    private  String receiverName;
    private  Long eventId;
    private  String title;
    private  boolean isMember;
    @NotBlank
    @NotNull
    private  long amount;
    private  String memo;

    public RegisterTransactionCommand(Long senderId, String senderName, Long receiverId, String receiverName, Long eventId, String title, boolean isMember, long amount, String memo) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.eventId = eventId;
        this.title = title;
        this.isMember = isMember;
        this.amount = amount;
        this.memo = memo;
        this.validateSelf(); // 생성자에서 유효성 검증
    }
}
