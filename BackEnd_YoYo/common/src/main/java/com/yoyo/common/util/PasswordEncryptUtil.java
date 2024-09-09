package com.yoyo.common.util;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.HashingException;
import java.security.MessageDigest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncryptUtil {

    public static String encrypt(String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());

            return bytesToHex(messageDigest.digest());
        } catch (Exception e){
            throw new HashingException(ErrorCode.PASSWORD_ENCRYPTION_FAILURE);
        }
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
