package com.example;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class TripleDesUtils {

    @Value("${encryption.key1}")
    private String encryptionKey1;

    @Value("${encryption.key2}")
    private String encryptionKey2;

    @Value("${encryption.algorithm}")
    private String encryptionAlgorithm;

    public String encrypt(String plainText) throws Exception {
        byte[] keyBytes1 = encryptionKey1.getBytes();
        byte[] keyBytes2 = encryptionKey2.getBytes();
        byte[] plaintext = plainText.getBytes();
        Cipher encipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        SecretKeySpec myKey = new SecretKeySpec(keyBytes1, encryptionAlgorithm);
        IvParameterSpec ivspec = new IvParameterSpec(keyBytes2);
        encipher.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
        byte[] cipherText = encipher.doFinal(plaintext);
        return Base64.encodeBase64String(cipherText);
    }

    public String decrypt(String encryptedText) throws Exception {
        byte[] keyBytes1 = encryptionKey1.getBytes();
        byte[] keyBytes2 = encryptionKey2.getBytes();
        byte[] encData = Base64.decodeBase64(encryptedText);
        Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        SecretKeySpec myKey = new SecretKeySpec(keyBytes1, encryptionAlgorithm);
        IvParameterSpec ivspec = new IvParameterSpec(keyBytes2);
        decipher.init(Cipher.DECRYPT_MODE, myKey, ivspec);
        byte[] plainText = decipher.doFinal(encData);
        return new String(plainText);
    }
}
