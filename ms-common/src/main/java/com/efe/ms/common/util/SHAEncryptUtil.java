package com.efe.ms.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

/**
 * SHA加解密工具类
 * @author Tianlong Liu
 * @2020年6月24日 上午11:30:39
 */
public final class SHAEncryptUtil {

	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String ALGORITHM_SHA_256 = "SHA-256";
	public static final String ALGORITHM_HMAC_SHA_256 = "HmacSHA256";
	public static final String ALGORITHM_SHA_512 = "SHA-512";

	/**
	 * SHA256 加密
	 * @param strText
	 * @return 加密结果
	 */
	public static String sha256(final String strText) {
		return sha(strText, ALGORITHM_SHA_256);
	}
	
	/**
	 * HmacSHA256 加密
	 * @param strText
	 * @param key
	 * @return
	 */
	public static String hmacSha256(final String strText,final String key) {
		return hmacSha256Encrypt(strText, key,ALGORITHM_HMAC_SHA_256);
	}

	/**
	 * SHA512 加密
	 * @param strText
	 * @return
	 */
	public static String sha512(final String strText) {
		return sha(strText, ALGORITHM_SHA_512);
	}

	/**
	 * 算法加密
	 * @param strText
	 * @param strType
	 * @return 加密结果
	 */
	private static String sha(final String strText, final String algorithm) {
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
	
	public static String hmacSha256Encrypt(String content, String key,String algorithm) {
		try {
			Mac hmac256 = Mac.getInstance(algorithm);
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(DEFAULT_CHARSET), algorithm);
			hmac256.init(secretKey);
			return Hex.encodeHexString(hmac256.doFinal(content.getBytes(DEFAULT_CHARSET)));
		} catch (Exception e) {
			throw new RuntimeException(algorithm + " 算法加密失败", e);
		}
	}
}