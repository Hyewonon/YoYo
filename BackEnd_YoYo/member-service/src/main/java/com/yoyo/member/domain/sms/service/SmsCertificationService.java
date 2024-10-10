<<<<<<<< HEAD:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/domain/sms/service/SmsCertificationService.java
package com.yoyo.member.domain.sms.service;

import com.yoyo.member.domain.member.repository.MemberRepository;
import com.yoyo.member.domain.sms.repository.SmsCertificationRepository;
========
package com.yoyo.member.application.service;

import com.yoyo.common.annotation.UseCase;
import com.yoyo.member.adapter.out.persistence.SmsCertificationRepository;
import com.yoyo.member.adapter.out.persistence.SpringDataMemberRepository;
import com.yoyo.member.application.port.in.member.SmsCertificationUseCase;
import java.util.Random;
>>>>>>>> 223e6a81c079b9f463bcdfe0cd8f9d5c1690a949:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/application/service/SmsCertificationService.java
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

@Slf4j
@UseCase
@RequiredArgsConstructor
<<<<<<<< HEAD:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/domain/sms/service/SmsCertificationService.java
public class SmsCertificationService  {
========
public class SmsCertificationService implements SmsCertificationUseCase {
>>>>>>>> 223e6a81c079b9f463bcdfe0cd8f9d5c1690a949:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/application/service/SmsCertificationService.java

    @Value("${cool-sms.send-phone-number}")
    private String SEND_PHONE_NUMBER;

    private final SmsCertificationRepository smsCertificationRepository;
<<<<<<<< HEAD:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/domain/sms/service/SmsCertificationService.java
    private final MemberRepository memberRepository;
========
    private final SpringDataMemberRepository memberRepository;
>>>>>>>> 223e6a81c079b9f463bcdfe0cd8f9d5c1690a949:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/application/service/SmsCertificationService.java

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

<<<<<<<< HEAD:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/domain/sms/service/SmsCertificationService.java
========
    @Override
>>>>>>>> 223e6a81c079b9f463bcdfe0cd8f9d5c1690a949:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/application/service/SmsCertificationService.java
    public boolean duplicatePhoneNumber(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

<<<<<<<< HEAD:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/domain/sms/service/SmsCertificationService.java
========
    @Override
>>>>>>>> 223e6a81c079b9f463bcdfe0cd8f9d5c1690a949:BackEnd_YoYo/member-service/src/main/java/com/yoyo/member/application/service/SmsCertificationService.java
    public boolean verifySmsCertification(String phoneNumber, String certificationNumber) {
        String storedCode = smsCertificationRepository.getSmsCertification(phoneNumber);
        if (storedCode != null && storedCode.equals(certificationNumber)) {
            smsCertificationRepository.deleteSmsCertification(phoneNumber);
            return true;
        }
        return false;
    }
}
