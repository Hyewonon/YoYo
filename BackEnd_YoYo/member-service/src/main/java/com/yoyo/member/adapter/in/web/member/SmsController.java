package com.yoyo.member.adapter.in.web.member;

import com.yoyo.member.application.port.in.member.SmsCertificationUseCase;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    private final SmsCertificationUseCase phoneValidationService;
    DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(COOLSMS_API_KEY, COOLSMS_SECRET, DOMAIN);
    }

    @PostMapping("/yoyo/members/send")
    @ResponseBody
    public ResponseEntity<?> sendSMS(@RequestParam String phoneNumber) {
        if (phoneValidationService.duplicatePhoneNumber(phoneNumber)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 전화번호");
        }
        String randomString = phoneValidationService.getValidationCode();
        Message message = phoneValidationService.getMessageForm(randomString, phoneNumber);
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        phoneValidationService.saveSmsCertification(phoneNumber, randomString);
        return ResponseEntity.ok("인증번호 전송안료");
    }

    @PostMapping("/yoyo/members/verify")
    @ResponseBody
    public ResponseEntity<?> verify(@RequestParam String phoneNumber, @RequestParam String code) {
        boolean isValid = phoneValidationService.verifySmsCertification(phoneNumber, code);
        if (isValid) {
            return ResponseEntity.ok("인증 완료");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 실패");
        }
    }
}
