package com.yoyo.banking.domain.account.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PayInfoDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long senderId;
        private Long receiverId;
        private Long amount;

        public static Request of(Long senderId, Long receiverId, Long amount) {
            return Request.builder()
                          .senderId(senderId)
                          .receiverId(receiverId)
                          .amount(amount)
                          .build();
        }
    }
}
