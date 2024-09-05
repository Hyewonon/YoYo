package com.yoyo.notification.global.exception.exceptionType;

import com.yoyo.notification.global.exception.CustomException;
import com.yoyo.notification.global.exception.ErrorCode;

public class NotificationException extends CustomException {
    public NotificationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
