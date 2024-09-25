package com.yoyo.common.exception.exceptionType;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;

public class NotificationException extends CustomException {
    public NotificationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
