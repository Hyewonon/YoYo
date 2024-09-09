package com.yoyo.member.application.port.in.member;


import net.nurigo.sdk.message.model.Message;

public interface SmsCertificationUseCase {

    String getValidationCode();

    Message getMessageForm(String randomString, String phoneNumber);

    boolean verifySmsCertification(String phoneNumber, String certificationNumber);

    void saveSmsCertification(String phoneNumber, String certificationNumber);

    boolean duplicatePhoneNumber(String phoneNumber);
}
