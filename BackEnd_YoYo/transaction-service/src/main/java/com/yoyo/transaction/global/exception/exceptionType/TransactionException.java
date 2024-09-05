package com.yoyo.transaction.global.exception.exceptionType;

import com.yoyo.transaction.global.exception.CustomException;
import com.yoyo.transaction.global.exception.ErrorCode;

public class TransactionException extends CustomException {
    public TransactionException(ErrorCode errorCode) {
        super(errorCode);
    }

}
