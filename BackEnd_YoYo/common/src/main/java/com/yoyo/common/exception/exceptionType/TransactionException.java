package com.yoyo.common.exception.exceptionType;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;

public class TransactionException extends CustomException {
    public TransactionException(ErrorCode errorCode) {
        super(errorCode);
    }

}
