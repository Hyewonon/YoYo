package com.yoyo.common.kafka.dto;

import com.yoyo.common.kafka.KafkaJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 비회원 결제 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO implements KafkaJson {

    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Long eventId;
    private String title;
    private Long amount;
    private String memo;
    private String description;

    public static PaymentDTO of(String senderName, Long receiverId, Long eventId, Long amount,
                                String memo) {
        return PaymentDTO.builder()
                         .senderName(senderName)
                         .receiverId(receiverId)
                         .eventId(eventId)
                         .amount(amount)
                         .memo(memo)
                         .build();
    }

    public static PaymentDTO of(String senderName, Long receiverId, Long eventId, Long amount,
                                String memo,String description, String title) {
        return PaymentDTO.builder()
                .senderName(senderName)
                .receiverId(receiverId)
                .eventId(eventId)
                .amount(amount)
                .memo(memo)
                .description(description)
                .title(title)
                .build();
    }
}
