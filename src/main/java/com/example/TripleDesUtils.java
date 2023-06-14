package com.example;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class TripleDesUtils {

    @Value("${encryption.key1}")
    private String encryptionKey1;

    @Value("${encryption.key2}")
    private String encryptionKey2;

    @Value("${encryption.algorithm}")
    private String encryptionAlgorithm;

    public String encrypt(String plainText) throws Exception {
        byte[] keyBytes1 = Base64.decodeBase64(encryptionKey1);
        byte[] keyBytes2 = Base64.decodeBase64(encryptionKey2);
        SecretKey secretKey1 = new SecretKeySpec(keyBytes1, encryptionAlgorithm);
        SecretKey secretKey2 = new SecretKeySpec(keyBytes2, encryptionAlgorithm);
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
        byte[] intermediate = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey2);
        byte[] encryptedBytes = cipher.doFinal(intermediate);
        return Base64.encodeBase64String(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        byte[] keyBytes1 = Base64.decodeBase64(encryptionKey1);
        byte[] keyBytes2 = Base64.decodeBase64(encryptionKey2);
        SecretKey secretKey1 = new SecretKeySpec(keyBytes1, encryptionAlgorithm);
        SecretKey secretKey2 = new SecretKeySpec(keyBytes2, encryptionAlgorithm);
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey2);
        byte[] intermediate = cipher.doFinal(Base64.decodeBase64(encryptedText));
        cipher.init(Cipher.DECRYPT_MODE, secretKey1);
        byte[] decryptedBytes = cipher.doFinal(intermediate);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
