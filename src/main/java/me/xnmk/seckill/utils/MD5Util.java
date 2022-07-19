package me.xnmk.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author:xnmk_zhan
 * @create:2022-07-15 15:57
 * @Description: MD5加密
 */
public class MD5Util {

    private static final String SALT = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFromPass(String inputPass) {
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    public static String fromPassToDBPass(String formPass, String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        String DBPass = fromPassToDBPass(fromPass, salt);
        return DBPass;
    }

    public static void main(String[] args) {
        // d3b1294a61a07da9b49b6e22b2cbd7f9
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(inputPassToDBPass("123456", SALT));
    }
}
