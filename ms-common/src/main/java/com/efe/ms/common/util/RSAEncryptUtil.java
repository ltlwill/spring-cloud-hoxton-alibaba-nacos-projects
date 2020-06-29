package com.efe.ms.common.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * RSA算法加解密工具
 * 
 * @author Tianlong Liu
 * @2020年6月29日 上午9:45:07
 */
public final class RSAEncryptUtil {

	public static final String ALGORITHM_NAME = "RSA";
	public static final String CHARSET_NAME = "UTF-8";
	public static final int KEY_SIZE = 2048;

	/**
	 * 生成RSA密匙对
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static keyPairDesc genKeyPair(final String password) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_NAME); // 生成公钥和私钥对，基于RSA算法生成对象
		keyPairGen.initialize(KEY_SIZE, new SecureRandom(password.getBytes())); // 初始化密钥对生成器
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); 
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); 
		String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		return new keyPairDesc(publicKeyStr, privateKeyStr);
	}

	/**
	 * RSA公匙加密
	 * @param str
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(final String str, final String publicKey) throws Exception {
		byte[] deBytes = Base64.getDecoder().decode(publicKey); // base64编码的公钥
		RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance(ALGORITHM_NAME)
				.generatePublic(new X509EncodedKeySpec(deBytes));
		Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
		cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
		return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(CHARSET_NAME)));
	}

	/**
	 * RSA私匙解密
	 * @param str
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(final String str, final String privateKey) throws Exception {
		byte[] inBytes = Base64.getDecoder().decode(str.getBytes(CHARSET_NAME));
		byte[] deBytes = Base64.getDecoder().decode(privateKey);
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance(ALGORITHM_NAME)
				.generatePrivate(new PKCS8EncodedKeySpec(deBytes));
		Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
		cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
		return new String(cipher.doFinal(inBytes), CHARSET_NAME);
	}

	public static class keyPairDesc {
		private String publicKey;
		private String privateKey;

		public keyPairDesc() {
		}

		public keyPairDesc(String publicKey, String privateKey) {
			this.publicKey = publicKey;
			this.privateKey = privateKey;
		}

		public String getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}

		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}
	}
}
