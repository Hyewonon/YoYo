package com.yoyo.event.global.exception.exceptionType;

import com.yoyo.event.global.exception.CustomException;
import com.yoyo.event.global.exception.ErrorCode;

public class EventException extends CustomException {
    public EventException(ErrorCode errorCode) {
        super(errorCode);
    }

}
