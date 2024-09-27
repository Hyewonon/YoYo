package com.yoyo.notification.domain.notification.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.common.kafka.dto.NotificationInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;
    private final String RELATION_TOPIC = "notification-relation-topic";
    private final String CREATE_MEMBER_EVENT = "create-member-event-topic";

    public void getFriendTag(MemberTagDTO request) {
        kafkaTemplate.send(RELATION_TOPIC, request);
    }

    public void createMemberEvent(NotificationInfoDTO request){
        kafkaTemplate.send(CREATE_MEMBER_EVENT, request);
    }
}
