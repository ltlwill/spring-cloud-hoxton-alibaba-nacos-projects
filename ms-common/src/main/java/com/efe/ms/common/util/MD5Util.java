package com.efe.ms.common.util;

import java.security.MessageDigest;

/**
 * MD5加解密工具
 * @2020年6月24日 上午11:02:31
 */
public final class MD5Util {

	private final static String ALGORITHM_NAME = "MD5";

	private static MessageDigest messageDigest = null;

	public static String encode(String inStr) {
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		if (byteArray == null || byteArray.length == 0) {
			return null;
		}
		byte[] md5Bytes = getMessageDigest().digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String decode(String inStr) {
		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return decode(s);
	}

	private static MessageDigest getMessageDigest() {
		if (messageDigest == null) {
			synchronized (MD5Util.class) {
				if (messageDigest == null) {
					messageDigest = instanceMassageDigest();
				}
			}
		}
		return messageDigest;
	}

	private static MessageDigest instanceMassageDigest() {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(ALGORITHM_NAME);
		} catch (Exception e) {
			throw new RuntimeException("获取加密算法失败", e);
		}
		return digest;
	}

}
