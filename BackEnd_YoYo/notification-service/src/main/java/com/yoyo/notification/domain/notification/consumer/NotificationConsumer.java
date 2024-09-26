package com.yoyo.notification.domain.notification.consumer;

import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.notification.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "notification-tag-topic", concurrency = "3")
    public void getRelationTag(MemberTagDTO response) {
        notificationService.completeMemberTag(response);
    }

}
