package com.yoyo.event.domain.event.consumer;

import com.yoyo.common.kafka.dto.AmountResponseDTO;
import com.yoyo.common.kafka.dto.MemberResponseDTO;
import com.yoyo.event.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventConsumer {

    private final EventService eventService;

    @KafkaListener(topics = "transaction-summary-topic", concurrency = "3")
    public void getTransactionSummary(AmountResponseDTO summary) {
        log.info("Received Transaction Summary: Total Transactions = {}, Total Amount = {}",
                 summary.getTransactionCount(), summary.getTotalAmount());
        eventService.completeTransactionSummary(summary);
    }

    @KafkaListener(topics = "member-name-topic", concurrency = "3")
    public void getTransactionSummary(MemberResponseDTO name) {
        log.info("RECEIVE MEMBER NAME");
        eventService.completeMemberName(name);
    }

}
