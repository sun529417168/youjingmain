package com.youjing.yjeducation.util;

import com.youjing.yjeducation.common.config.YJConfig;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 15:38
 */
public class DES {
    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};

    private static String encryptKey = YJConfig.ENCRYPTKEY;
    public static String encryptDES(String encryptString)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encode(encryptedData);
    }

    public static String decryptDES(String decryptString)
            throws Exception {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }
}
