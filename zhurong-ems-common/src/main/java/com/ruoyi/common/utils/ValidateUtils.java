package com.ruoyi.common.utils;

/**
 * 校验工具类
 * @author ruoyi
 */
public class ValidateUtils extends org.apache.commons.lang3.StringUtils {

	public static boolean validatePhoneNo(String phoneNumber) {
		// 定义手机号的正则表达式
		String regex = "^1[3-9]\\d{9}$";
		// 使用正则表达式检查手机号
		return phoneNumber.matches(regex);
	}

	public static boolean validateUrl(String value) {
		// 定义手机号的正则表达式
		String regex =
				"^(https?|ftp):\\/\\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}|localhost)(:[0-9]+)?(\\/[^\\s]*)?$";        // 使用正则表达式检查手机号
		return value.matches(regex);
	}

}
