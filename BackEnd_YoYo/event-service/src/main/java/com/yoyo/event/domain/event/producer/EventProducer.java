package com.yoyo.event.domain.event.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.AmountRequestDTO;
import com.yoyo.common.kafka.dto.MemberRequestDTO;
import com.yoyo.common.kafka.dto.NotificationCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventProducer {

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;
    private final String TRANSACTION_TOPIC = "transaction-topic";
    private final String MEMBER_TOPIC = "event-member-name-topic";
    private final String CREATE_NOTIFICATION = "create-notification";
    private final String GET_RELATION_IDS = "get-relations-ids";

    public void sendEventId(AmountRequestDTO event) {
        kafkaTemplate.send(TRANSACTION_TOPIC, event);
    }

    public void getMemberName(MemberRequestDTO event) {
        kafkaTemplate.send(MEMBER_TOPIC, event);
    }

    public void sendEventNotification(NotificationCreateDTO request) {
        kafkaTemplate.send(CREATE_NOTIFICATION, request);
    }

    public void getRelationIds(MemberRequestDTO request){
        kafkaTemplate.send(GET_RELATION_IDS, request);
    }

}
