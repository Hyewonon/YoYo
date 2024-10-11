package com.yoyo.common.kafka.dto;

import com.yoyo.common.kafka.KafkaJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayInfoRequestToTransactionDTO implements KafkaJson {

    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Long eventId;
    private String title;
    private Long amount;

    public static PayInfoRequestToTransactionDTO of(Long senderId, String senderName, Long receiverId,
                                                    String receiverName, Long eventId,
                                                    String title, Long amount) {
        return PayInfoRequestToTransactionDTO.builder()
                                             .senderId(senderId)
                                             .senderName(senderName)
                                             .receiverId(receiverId)
                                             .receiverName(receiverName)
                                             .eventId(eventId)
                                             .title(title)
                                             .amount(amount)
                                             .build();
    }
}
