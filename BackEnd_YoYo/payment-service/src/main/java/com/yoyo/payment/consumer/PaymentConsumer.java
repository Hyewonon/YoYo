package com.yoyo.payment.consumer;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.kafka.dto.ReceiverRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentConsumer {
    private ReceiverRequestDTO receiverRequestDTO;
    @KafkaListener(topics = "receiverId-eventId", concurrency = "3")
    public void sendReceiverId(ReceiverRequestDTO requestDTO) {
        this.receiverRequestDTO = requestDTO;
        log.info("개최자 : {}", requestDTO.getReceiverId());
    }
    public Long getReceiverId() {
        if (this.receiverRequestDTO != null) {
            return this.receiverRequestDTO.getReceiverId();
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
    }
}
