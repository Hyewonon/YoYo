package com.yoyo.common.exception.exceptionType;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;

public class BankingException extends CustomException {

    public BankingException(ErrorCode errorCode) {
        super(errorCode);
    }
}