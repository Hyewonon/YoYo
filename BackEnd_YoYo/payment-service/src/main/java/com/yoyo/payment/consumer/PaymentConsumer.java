package com.yoyo.payment.consumer;

import com.yoyo.common.exception.CustomException;
import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.kafka.dto.ReceiverRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {
    private final Map<Long, CompletableFuture<ReceiverRequestDTO>> futureMap = new ConcurrentHashMap<>();

    public CompletableFuture<ReceiverRequestDTO> createReceiverFuture(Long eventId) {
        CompletableFuture<ReceiverRequestDTO> future = new CompletableFuture<>();
        futureMap.put(eventId, future);
        return future;
    }

    @KafkaListener(topics = "receiverId-eventId", concurrency = "3")
    public void sendReceiverId(ReceiverRequestDTO requestDTO) {
        CompletableFuture<ReceiverRequestDTO> future = futureMap.get(requestDTO.getEventId());
        if (future != null) {
            future.complete(requestDTO);
        } else {
            log.error("EventId {}에 해당하는 항목을 찾을 수 없습니다.", requestDTO.getEventId());
        }
    }

    public ReceiverRequestDTO getReceiverId(Long eventId) {
        try {
            CompletableFuture<ReceiverRequestDTO> future = createReceiverFuture(eventId);
            return future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.KAFKA_ERROR);
        } finally {
            futureMap.remove(eventId);
        }
    }
}
