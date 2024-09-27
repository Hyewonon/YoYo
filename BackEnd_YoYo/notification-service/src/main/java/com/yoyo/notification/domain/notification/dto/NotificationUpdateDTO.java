package com.yoyo.notification.domain.notification.dto;

import com.yoyo.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationUpdateDTO {

    private Long notificationId;
    private Boolean isRegister; // 이벤트 등록할 것인가

    public static NotificationUpdateDTO of(Notification notification) {
        return NotificationUpdateDTO.builder()
                                    .notificationId(notification.getId())
                                    .isRegister(notification.getIsRegister())
                                    .build();
    }
}
