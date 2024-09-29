package com.yoyo.notification.domain.notification.consumer;

import com.yoyo.common.kafka.dto.MemberTagDTO;
import com.yoyo.common.kafka.dto.NotificationCreateDTO;
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

    private final String GET_RELATION_INFO = "notification-tag-topic";
    private final String CREATE_NOTIFICATION = "create-notification";

    @KafkaListener(topics = GET_RELATION_INFO, concurrency = "3")
    public void getRelationTag(MemberTagDTO response) {
        notificationService.completeMemberTag(response);
    }

    @KafkaListener(topics = CREATE_NOTIFICATION, concurrency = "3")
    public void createNotification(NotificationCreateDTO request) {
        notificationService.createNotification(request);
    }

}
