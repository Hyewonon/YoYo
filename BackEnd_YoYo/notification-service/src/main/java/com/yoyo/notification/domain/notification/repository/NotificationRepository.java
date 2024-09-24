package com.yoyo.notification.domain.notification.repository;

import com.yoyo.notification.entity.Notification;
import com.yoyo.notification.entity.NotificationType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByReceiverIdAndTypeOrderByCreatedAtDesc(Long receiverId, NotificationType type);

}
