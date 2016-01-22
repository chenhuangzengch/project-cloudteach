package net.xuele.cloudteach.web.common;

import net.xuele.common.exceptions.CloudteachException;
import net.xuele.common.exceptions.MemberException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

/**
 * CloudTeachEncryptUtil
 *
 * @author sunxh
 * @date 15/8/13
 */
public class CloudTeachEncryptUtil {

//    @Value("${cloudteach.encryptutil.passwordkey}")
    private static String PASSWORD_KEY = "xuelezhongguodev";

    public static String aesEncrypt(String password) {
        String str;
        try {
            byte[] keyBytes = Arrays.copyOf(PASSWORD_KEY.getBytes("ASCII"), 16);

            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] cleartext = password.getBytes("UTF-8");
            byte[] ciphertextBytes = cipher.doFinal(cleartext);

            str = new String(Hex.encodeHex(ciphertextBytes)).toUpperCase();

        } catch (Exception e) {
            throw new CloudteachException("aes加密失败，具体错误信息为：" + e.getMessage());
        }
        return str;
    }

    public static String aesDecrypt(String encryptResult) {
        String str;
        try {
            byte[] keyBytes = Arrays.copyOf(PASSWORD_KEY.getBytes("ASCII"), 16);

            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] cleartext = Hex.decodeHex(encryptResult.toCharArray());
            byte[] ciphertextBytes = cipher.doFinal(cleartext);

            str = new String(ciphertextBytes, "UTF-8");

        } catch (Exception e) {
            throw new CloudteachException("aes解密失败，具体信息错误为：" + e.getMessage());
        }
        return str;
    }

    /**
     * 加密方法,到时候统一改
     *
     * @param password 明文密码
     * @return 加密后的密码
     */
    public static String encrypt(String password) {
//        return password;
        return aesEncrypt(password);
    }

    /**
     * 密码解密，到时候统一改
     *
     * @param password
     * @return
     */
    public static String decrypt(String password) {
//        return password;
        return aesDecrypt(password);
    }

    public static void main(String[] args) {
        System.out.println(decrypt("9a0e8ce68426ee1e3bf2ff83a8908ab7"));
        System.out.println(encrypt("10001"));
        System.out.println(encrypt("1000110003"));
    }

}
