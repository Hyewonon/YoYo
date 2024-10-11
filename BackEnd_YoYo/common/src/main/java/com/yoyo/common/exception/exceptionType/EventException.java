package com.yoyo.common.exception.exceptionType;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;

public class EventException extends CustomException {

    public EventException(ErrorCode errorCode) {
        super(errorCode);
    }
}