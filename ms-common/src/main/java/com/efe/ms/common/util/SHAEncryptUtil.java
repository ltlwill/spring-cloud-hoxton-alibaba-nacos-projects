package com.efe.ms.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA加解密工具类
 * @author Tianlong Liu
 * @2020年6月24日 上午11:30:39
 */
public final class SHAEncryptUtil {

	public static final String ALGORITHM_SHA_256 = "SHA-256";
	public static final String ALGORITHM_SHA_512 = "SHA-512";

	/**
	 * SHA256 加密
	 * @param strText
	 * @return 加密结果
	 */
	public static String SHA256(final String strText) {
		return SHA(strText, ALGORITHM_SHA_256);
	}

	/**
	 * SHA512 加密
	 * @param strText
	 * @return
	 */
	public static String SHA512(final String strText) {
		return SHA(strText, ALGORITHM_SHA_512);
	}

	/**
	 * 算法加密
	 * @param strText
	 * @param strType
	 * @return 加密结果
	 */
	private static String SHA(final String strText, final String algorithm) {
		String strResult = null;
		if (strText != null && strText.length() > 0) {
			try {
				MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
				messageDigest.update(strText.getBytes());
				byte byteBuffer[] = messageDigest.digest();
				StringBuffer strHexString = new StringBuffer();
				for (int i = 0; i < byteBuffer.length; i++) {
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1) {
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				strResult = strHexString.toString();
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
		return strResult;
	}
}