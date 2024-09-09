package com.yoyo.common.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse {

    private final Boolean isSuccess;
    private final String message;

    public static CommonResponse of(Boolean isSuccess, String message) {
        return CommonResponse.builder()
                             .isSuccess(isSuccess)
                             .message(message)
                             .build();
    }
}
