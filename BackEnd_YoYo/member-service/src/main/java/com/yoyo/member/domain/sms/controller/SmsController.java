package com.yoyo.member.domain.sms.controller;

import com.yoyo.common.response.ApiResponse;
import com.yoyo.member.domain.sms.service.SmsCertificationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SmsController {

    @Value("${cool-sms.api-key}")
    private String COOLSMS_API_KEY;
    @Value("${cool-sms.api-secret}")
    private String COOLSMS_SECRET;
    @Value("${cool-sms.domain}")
    private String DOMAIN;

    private final SmsCertificationService smsCertificationService;
    DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(COOLSMS_API_KEY, COOLSMS_SECRET, DOMAIN);
    }

    @PostMapping("/yoyo/members/send")
    @ResponseBody
    public ResponseEntity<?> sendSMS(@RequestParam String phoneNumber) {
        ApiResponse<String> res;
        if (smsCertificationService.duplicatePhoneNumber(phoneNumber)) {
            res = new ApiResponse<>(
                    HttpStatus.CONTINUE.value(),
                    "이미 등록된 전화번호",
                    phoneNumber
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        }
        String randomString = smsCertificationService.getValidationCode();
        Message message = smsCertificationService.getMessageForm(randomString, phoneNumber);
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        smsCertificationService.saveSmsCertification(phoneNumber, randomString);
        res = new ApiResponse<>(
                HttpStatus.OK.value(),
                "인증번호 전송완료",
                phoneNumber
        );
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/yoyo/members/verify")
    @ResponseBody
    public ResponseEntity<?> verify(@RequestParam String phoneNumber, @RequestParam String code) {
        boolean isValid = smsCertificationService.verifySmsCertification(phoneNumber, code);
        ApiResponse<Boolean> response;
        if (isValid) {
            response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "인증 완료",
                    true
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "인증 실패",
                    false
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
