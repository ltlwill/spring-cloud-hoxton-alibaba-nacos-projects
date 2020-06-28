package com.efe.ms.common;

import org.junit.Test;

import com.efe.ms.common.util.AESUtil;
import com.efe.ms.common.util.EncryptUtil;

public class AlgorithmTest {
	
	@Test
	public void test1() {
		String str = "abcd111";
		System.out.println(EncryptUtil.SHA256(str));
		System.out.println(EncryptUtil.SHA512(str));
	}
	
	/**
	 * @see https://www.jianshu.com/p/de81059a9e97
	 */
	@Test
	public void test2() {
		/**
		 * 这是因为某些国家的进口管制限制，JDK默认的加解密有一定的限制。
		比如默认不允许 256 位密钥的 AES 加解密，解决方法就下载官方JCE无限制强度加密策略文件，覆盖即可
		 *  但从Java 1.8.0_151和1.8.0_152开始，为JVM启用无限制强度管辖策略 有了一种新的更简单的方法。如果不启用此功能，则不能使用AES-256：
		在 jre/lib/security 文件夹中查找文件 java.security，现在用文本编辑器打开java.security，并找到定义java安全性属性crypto.policy的行，
		它可以有两个值limited或unlimited - 默认值是limited。将其设置为：crypto.policy=unlimited

		 */
		String str = "123456";
		System.out.println("原始值：" + str);
		String key = "12@$**^^%#@221&&@^@!@872120921@***!!@~~!";
		String res = AESUtil.encrypt(str, key);
		System.out.println("AES加密后：" + res);
		System.out.println("AES解密后：" + AESUtil.decrypt(res, key));
	}

}
