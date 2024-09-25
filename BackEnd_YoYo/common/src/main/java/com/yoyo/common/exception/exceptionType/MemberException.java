package com.yoyo.common.exception.exceptionType;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;

public class MemberException extends CustomException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
