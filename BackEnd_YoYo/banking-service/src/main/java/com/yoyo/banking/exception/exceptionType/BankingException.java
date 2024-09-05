package com.yoyo.banking.exception.exceptionType;

import com.yoyo.banking.exception.CustomException;
import com.yoyo.banking.exception.ErrorCode;

public class BankingException extends CustomException {

    public BankingException(ErrorCode errorCode) {
        super(errorCode);
    }
}