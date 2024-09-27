package com.yoyo.payment.controller;

import com.yoyo.common.kafka.dto.PaymentDTO;
import com.yoyo.common.kafka.dto.TransactionDTO;
import com.yoyo.payment.producer.PaymentProducer;
import com.yoyo.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PaymentProducer producer;

    @Value("${payment.secret}")
    private String SECRETKEY;
    @Value("${payment.url}")
    private String PAYMENTURL;
    private final PaymentService paymentService;

    @PostMapping(value = "/confirm/payment", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        String senderName;
        Long receiverId;
        Long eventId;
        String description;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
            senderName = (String) requestData.get("senderName");
            receiverId = (Long) requestData.get("receiverId");
            eventId = (Long) requestData.get("eventId");
            description = (String) requestData.get("description");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        };
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((SECRETKEY + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        URL url = new URL(PAYMENTURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);


        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // TODO: 결제 성공 비즈니스 로직 구현
        /*
        1. PaymentDTO(senderName, receiverId, eventId, title, amount, memo) 생성
        2. [NoMember] 비회원 생성 -> Relation 생성
        -> [Banking] 받은 사람 Pay 값 올려주기
        -> [Transaction] 기록 : 받은 사람 ID, 금액, 메모
         */
        log.info("들어오는 데이터 : {}", jsonBody );
        log.info("데이터 : {}",obj);
        producer.sendNoMemberPayment(PaymentDTO.of(senderName, receiverId, eventId, Long.parseLong(amount), description));

        /*
         * TODO : 결제 실패 비즈니스 로직 구현 
         */
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);
    }

    @PostMapping("/yoyo/payment/success")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transaction) {
        paymentService.sendTransaction(transaction);
        paymentService.sendRelation(transaction);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/yoyo/payment/test")
    public ResponseEntity<?> paymentTest(){
        producer.sendNoMemberPayment(PaymentDTO.of("이찬진", 999999998L, 10L, 500000L, "추카추카"));
        return ResponseEntity.ok().build();
    }
}
