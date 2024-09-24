package com.yoyo.transaction.adapter.out.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.IncreaseAmountDTO;
import com.yoyo.common.kafka.dto.TransactionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionProducer {
    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;
    private final KafkaTemplate<String, KafkaJson> amountKafkaTemplate;
    public void sendTransactionSummary(TransactionResponseDTO responseDTO) {
        kafkaTemplate.send("transaction-summary-topic", responseDTO);
    }
    public void sendRelation(IncreaseAmountDTO amount) {
        amountKafkaTemplate.send("transaction-register-topic", amount);
    }
}
