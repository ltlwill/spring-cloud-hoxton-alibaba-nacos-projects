package com.efe.ms.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密工具
 * 
 * @author Tianlong Liu
 * @2020年6月24日 上午11:25:30
 */
public final class AESUtil {

	private static final String KEY_ALGORITHM = "AES";
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";// 默认的加密算法
	private static final String DEFAULT_CHAR_SET = "UTF-8";
	private static final int BIT_AES_256 = 256;

	/**
	 * AES 加密
	 * 
	 * @param content
	 * @param key
	 * @return
	 */
	public static String encrypt(final String content, final String key) {
		try {
			byte[] bytes = content.getBytes(DEFAULT_CHAR_SET);
			byte[] result = getCipher(key,Cipher.ENCRYPT_MODE).doFinal(bytes);
			return Base64.getEncoder().encodeToString(result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * AES 解密
	 * 
	 * @param content
	 * @param key
	 * @return
	 */
	public static String decrypt(final String content, final String key) {
		try {
			byte[] result = getCipher(key,Cipher.DECRYPT_MODE).doFinal(Base64.getDecoder().decode(content));
			return new String(result, DEFAULT_CHAR_SET);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Cipher getCipher(final String key,final int mode) {
		try {
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			cipher.init(mode, getSecretKey(key));
			return cipher;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成加密秘钥
	 *
	 * @return
	 */
	private static SecretKeySpec getSecretKey(final String key) {
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			kg.init(BIT_AES_256, new SecureRandom(key.getBytes()));
			SecretKey secretKey = kg.generateKey();
			return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}










