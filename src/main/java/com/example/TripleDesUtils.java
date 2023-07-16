package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
@Slf4j
public class TripleDesUtils {

    private final String encryptionAlgorithm = "DESede";

    public String encrypt(String plainText,String encryptionKey1, String encryptionKey2) throws Exception {
        byte[] keyBytes1 = encryptionKey1.getBytes();
        byte[] keyBytes2 = encryptionKey2.getBytes();
        byte[] plaintext = plainText.getBytes();
        Cipher encipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        SecretKeySpec myKey = new SecretKeySpec(keyBytes1, encryptionAlgorithm);
        IvParameterSpec ivspec = new IvParameterSpec(keyBytes2);
        encipher.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
        byte[] cipherText = encipher.doFinal(plaintext);
        String encryptedPlainText = Base64.encodeBase64String(cipherText);
        log.info(" Encrypted String : {}", encryptedPlainText);
        return encryptedPlainText;
    }

    public String decrypt(String encryptedText, String encryptionKey1, String encryptionKey2) throws Exception {
        byte[] keyBytes1 = encryptionKey1.getBytes();
        byte[] keyBytes2 = encryptionKey2.getBytes();
        byte[] encData = Base64.decodeBase64(encryptedText);
        Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        SecretKeySpec myKey = new SecretKeySpec(keyBytes1, encryptionAlgorithm);
        IvParameterSpec ivspec = new IvParameterSpec(keyBytes2);
        decipher.init(Cipher.DECRYPT_MODE, myKey, ivspec);
        byte[] plainText = decipher.doFinal(encData);
        String decryptedPlainText =  new String(plainText);
        log.info(" Decrypted String : {}", decryptedPlainText);
        return  decryptedPlainText;
    }
}
