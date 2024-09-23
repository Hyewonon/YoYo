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
    NOT_FOUND_USER_KEY("user key가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_ACCOUNT("계좌를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    EXCEEDS_PAY_BALANCE("충전 금액을 초과하였습니다.", HttpStatus.BAD_REQUEST),

    // 일정 관련 에러
    DUPLICATE_MEMBER_EVENT("이미 등록된 일정입니다.", HttpStatus.CONFLICT),
    FORBIDDEN_EVENT("이벤트 접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
    ;

    private final String message;
    private final HttpStatus httpStatus;
}
