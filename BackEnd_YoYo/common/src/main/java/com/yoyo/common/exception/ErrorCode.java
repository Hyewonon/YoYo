package com.yoyo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 예기치못한 에러 발생 시 반환 에러
    UNEXPECTED_ERROR("예기치 못한 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("해당 데이터 없음", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
