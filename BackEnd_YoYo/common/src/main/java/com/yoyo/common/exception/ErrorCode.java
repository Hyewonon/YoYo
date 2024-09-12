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

    // 비밀번호 암호화 관련 에러
    PASSWORD_ENCRYPTION_FAILURE("암호화 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 뱅킹서비스 관련 에러
    NOT_FOUND_BANK("존재하지 않는 은행입니다.", HttpStatus.NOT_FOUND),
    
    // 일정 관련 에러
    DUPLICATE_MEMBER_EVENT("이미 등록된 일정입니다.", HttpStatus.CONFLICT),
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
