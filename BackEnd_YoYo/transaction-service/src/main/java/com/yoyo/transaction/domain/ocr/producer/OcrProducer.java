package com.yoyo.transaction.domain.ocr.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OcrProducer {
    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;
    public void sendMatchRelation(TransactionDTO.MatchRelation matchRelation) {
        kafkaTemplate.send("match-relation", matchRelation);
    }
}
