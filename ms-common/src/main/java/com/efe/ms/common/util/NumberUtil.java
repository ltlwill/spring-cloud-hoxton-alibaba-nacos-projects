package com.efe.ms.billservice.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * 数字工具
 * 
 * @author Tianlong Liu
 * @date 2020年9月29日 下午7:45:21
 */
public final class NumberUtil {
	
	private static final String NEGATIVE_NUMBER_CHAR = "-";
	private static final String COMMA_CHAR = ",";

	/**
	 * 货币字符转数字（如：-US$39.99 -> 39.99）
	 * @param str
	 * @return
	 */
	public static BigDecimal currencyToBigDecimal(String str) {
		if(str == null || "".equals(str.trim())) {
			return null;
		}
		String prefix = "";
		if(str.contains(NEGATIVE_NUMBER_CHAR)) {
			prefix = NEGATIVE_NUMBER_CHAR;
			str = str.replaceAll(NEGATIVE_NUMBER_CHAR, "");
		}
		int idx = getFirstNumberCharIndex(str);
		str = prefix + str.substring(idx, str.length());
		return BigDecimal.valueOf(Double.valueOf(str.replaceAll(COMMA_CHAR, "")));
	}
	
	/**
	 * 从货币字符串中获取 货币类型（如：-US$39.99 -> US$）
	 * @param str
	 * @return
	 */
	public static String getCurrency(String str) {
		if(str == null || "".equals(str.trim())) {
			return null;
		}
		if(str.contains(NEGATIVE_NUMBER_CHAR)) {
			str = str.replaceAll(NEGATIVE_NUMBER_CHAR, "");
		}
		int idx = getFirstNumberCharIndex(str);
		return idx > 0 ? str.substring(0, idx) : "";
	}
	
	private static int getFirstNumberCharIndex(String str) {
		Pattern pattern = Pattern.compile("\\d");
		char[] chars = str.toCharArray();
		int len = chars.length;
		int idx = -1;
		for(int i=0;i<len;i++) {
			if(pattern.matcher(chars[i] + "").matches()) {
				idx = i;
				break;
			}
		}
		return idx;
	}
}






















