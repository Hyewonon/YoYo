package com.yoyo.member.domain.sms.service;

import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.sms.repository.SmsCertificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsCertificationService  {

    @Value("${cool-sms.send-phone-number}")
    private String SEND_PHONE_NUMBER;

    private final SmsCertificationRepository smsCertificationRepository;
    private final MemberRepository memberRepository;

    public String getValidationCode() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

    public Message getMessageForm(String randomString, String phoneNumber) {
        Message message = new Message();
        message.setFrom(SEND_PHONE_NUMBER);
        message.setTo(phoneNumber);
        message.setText("[YoYo] 아래의 인증번호를 입력해주세요 \n [" + randomString + "]");
        return message;
    }

    public void saveSmsCertification(String phoneNumber, String certificationNumber) {
        smsCertificationRepository.createSmsCertification(phoneNumber, certificationNumber);
    }

    public boolean duplicatePhoneNumber(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean verifySmsCertification(String phoneNumber, String certificationNumber) {
        String storedCode = smsCertificationRepository.getSmsCertification(phoneNumber);
        if (storedCode != null && storedCode.equals(certificationNumber)) {
            smsCertificationRepository.deleteSmsCertification(phoneNumber);
            return true;
        }
        return false;
    }
}
