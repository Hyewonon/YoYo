package com.yoyo.banking.domain.account.consumer;

import com.yoyo.banking.domain.account.service.PayService;
import com.yoyo.common.kafka.dto.AmountResponseDTO;
import com.yoyo.common.kafka.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayConsumer {

    private final String UPDATE_YOYO_PAY_BY_NO_MEMBER = "update-yoyo-pay-by-no-member";

    private final PayService payService;

    @KafkaListener(topics = UPDATE_YOYO_PAY_BY_NO_MEMBER, concurrency = "3")
    public void updatePayForNoMember(PaymentDTO request) {
        payService.noMemberPayment(request);
    }
}
