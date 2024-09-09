package com.yoyo.common.exception.exceptionType;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;

public class HashingException extends CustomException {

    public HashingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
