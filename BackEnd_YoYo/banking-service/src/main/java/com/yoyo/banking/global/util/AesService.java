package com.yoyo.banking.global.util;

import com.yoyo.common.exception.ErrorCode;
import com.yoyo.common.exception.exceptionType.BankingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.Cipher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AesService {

    @Value("${ssafy.bank.aes-secret}")
    private String AES_SECRET_KEY;

    private SecretKeySpec key;

    public String encrypt(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            key = new SecretKeySpec(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptedByte = cipher.doFinal(plainText.getBytes("UTF-8"));

            return Base64.getEncoder().encodeToString(encryptedByte);
        } catch (Exception e) {
            throw new BankingException(ErrorCode.PASSWORD_ENCRYPTION_FAILURE);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            key = new SecretKeySpec(AES_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedByte = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

            return new String(decryptedByte, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new BankingException(ErrorCode.PASSWORD_DECRYPTION_FAILURE);
        }
    }
}
