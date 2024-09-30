package com.yoyo.notification.global.exception;

import com.yoyo.common.dto.response.BodyValidationExceptionResopnse;
import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.ErrorResponse;
import com.yoyo.common.exception.exceptionType.BankingException;
import com.yoyo.common.exception.exceptionType.EventException;
import com.yoyo.common.exception.exceptionType.MemberException;
import com.yoyo.common.exception.exceptionType.NotificationException;
import com.yoyo.common.exception.exceptionType.TransactionException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("예상치 못한 오류 : [UnexpectedException] : ", e);
        return ErrorResponse.toResponseEntity(ErrorCode.UNEXPECTED_ERROR);
    }

    @ExceptionHandler(BankingException.class)
    public ResponseEntity<ErrorResponse> handleBankingException(BankingException e) {
        log.error("[BankingException] : {} is occurred", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(EventException.class)
    public ResponseEntity<ErrorResponse> handleEventException(EventException e) {
        log.error("[EventException] : {} is occurred", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(NotificationException e) {
        log.error("[MemberException] : {} is occurred", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorResponse> handleNotificationException(NotificationException e) {
        log.error("[NotificationException] : {} is occurred", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponse> handleTransactionException(TransactionException e) {
        log.error("[TransactionException] : {} is occurred", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    /*
     * * RequestBody dto에 대한 validation 검증 예외 처리
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<BodyValidationExceptionResopnse>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        log.error("field validation error : [MethodArgumentNotValidException] : ", e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<BodyValidationExceptionResopnse> errorResponse
                = fieldErrors.stream()
                             .map(error -> BodyValidationExceptionResopnse.builder()
                                                                           .field(error.getField())
                                                                           .rejectedValue(error.getRejectedValue())
                                                                           .errorMessage(error.getDefaultMessage())
                                                                           .build())
                             .toList();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
