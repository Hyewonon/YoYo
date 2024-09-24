package com.yoyo.banking.domain.account.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoyo.banking.domain.account.dto.pay.PayInfoDTO;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.BankingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayProducer {

    private final String UPDATE_RELATION_TOPIC = "pay-update-relation-topic";


    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendPayInfo(PayInfoDTO.Request request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String stringRequest = objectMapper.writeValueAsString(request);
            kafkaTemplate.send(UPDATE_RELATION_TOPIC, stringRequest);
        } catch (JsonProcessingException e) {
            throw new BankingException(ErrorCode.KAFKA_ERROR);
        }
    }

}
