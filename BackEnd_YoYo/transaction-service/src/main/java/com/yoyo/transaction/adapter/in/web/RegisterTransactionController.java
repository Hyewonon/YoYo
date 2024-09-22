package com.yoyo.transaction.adapter.in.web;

import com.yoyo.common.annotation.WebAdapter;
import com.yoyo.transaction.application.port.in.RegisterTransactionCommand;
import com.yoyo.transaction.application.port.in.RegisterTransactionUseCase;
import com.yoyo.transaction.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterTransactionController {

    private final RegisterTransactionUseCase registerTransactionUseCase;

    @PostMapping("/yoyo/transactions/register")
    Transaction registerMember(@RequestBody RegisterTransactionRequest request, Long memberId) {
        // request -> command
        RegisterTransactionCommand command = RegisterTransactionCommand.builder()
                .senderId(request.getSenderId())
                .senderName(request.getSenderName())
                .receiverId(request.getReceiverId())
                .receiverName(request.getReceiverName())
                .eventId(request.getEventId())
                .isMember(request.isMember())
                .title(request.getTitle())
                .amount(request.getAmount())
                .memo(request.getMemo())
                .build();
        return registerTransactionUseCase.registerTransaction(command, memberId);
    }
}
