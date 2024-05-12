package com.mumu.studentbankmanagement;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class Encryption {
    private static String key="JJLin19810327ljj";
    public static String encrypt(String data) throws Exception {
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Encryption.encrypt("2"));
    }

}
