package com.yoyo.member.domain.relation.producer;

import com.yoyo.common.kafka.KafkaJson;
import com.yoyo.common.kafka.dto.MemberTagDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelationProducer {

    private final KafkaTemplate<String, KafkaJson> kafkaTemplate;

    public void sendMemberTag(MemberTagDTO response) {
        kafkaTemplate.send("notification-tag-topic", response);
    }

}
