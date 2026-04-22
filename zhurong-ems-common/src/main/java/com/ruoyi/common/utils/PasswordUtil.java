package com.ruoyi.common.utils;

import cn.hutool.core.util.RandomUtil;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2024/8/30 <br>
 * @author Double
 * @version 1.0.0
 */
public class PasswordUtil {

	 // 定义字符池，包含大写字母、小写字母、数字和特殊字符
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()+[]{}.<>?";

    public static void main(String[] args) {
        // 生成一个包含大小写字母、数字和特殊字符的密码
        String password = generatePassword(12);
        System.out.println("生成的密码: " + password);
    }

    public static String generatePassword(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("密码长度必须至少为 4");
        }

        StringBuilder password = new StringBuilder(length);

        // 确保密码包含至少一个字符类别
        password.append(RandomUtil.randomChar(UPPERCASE));
        password.append(RandomUtil.randomChar(LOWERCASE));
        password.append(RandomUtil.randomChar(DIGITS));
        password.append(RandomUtil.randomChar(SPECIAL_CHARS));

        // 补充其他随机字符
        String allChars = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;
        for (int i = 4; i < length; i++) {
            password.append(RandomUtil.randomChar(allChars));
        }

        // 打乱密码字符顺序
        return  password.toString() ;
    }


}
