package com.yoyo.smsauth.service;


import net.nurigo.sdk.message.model.Message;

public interface SmsCertificationService {

    String getValidationCode();

    Message getMessageForm(String randomString, String phoneNumber);

    boolean verifySmsCertification(String phoneNumber, String certificationNumber);

    void saveSmsCertification(String phoneNumber, String certificationNumber);
}
