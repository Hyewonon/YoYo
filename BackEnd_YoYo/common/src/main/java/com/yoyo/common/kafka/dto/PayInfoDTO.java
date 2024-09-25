package com.yoyo.common.kafka.dto;

import com.yoyo.common.kafka.KafkaJson;
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
    public static class RequestToMember implements KafkaJson {

        private Long senderId;
        private Long receiverId;
        private Long amount;

        public static RequestToMember of(Long senderId, Long receiverId, Long amount) {
            return RequestToMember.builder()
                                  .senderId(senderId)
                                  .receiverId(receiverId)
                                  .amount(amount)
                                  .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestToTransaction implements KafkaJson {

        private Long senderId;
        private String senderName;
        private Long receiverId;
        private String receiverName;
        private Long eventId;
        private String title;
        private Long amount;

        public static RequestToTransaction of(Long senderId, Long receiverId, String receiverName, Long eventId,
                                              String title, Long amount) {
            return RequestToTransaction.builder()
                                       .senderId(senderId)
                                       .receiverId(receiverId)
                                       .receiverName(receiverName)
                                       .eventId(eventId)
                                       .title(title)
                                       .amount(amount)
                                       .build();
        }
    }
}
