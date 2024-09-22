package com.yoyo.event.domain.event.consumer;

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
    @KafkaListener(topics = "transaction-summary-topic", groupId = "event-service")
    public void getTransactionSummary(TransactionSummaryResponse summary) {
        log.info("Received Transaction Summary: Total Transactions = {}, Total Amount = {}",
                summary.getTransactionCount(), summary.getTotalAmount());
        eventService.completeTransactionSummary(summary);
    }
}
