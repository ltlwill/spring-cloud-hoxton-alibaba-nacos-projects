package com.efe.ms.common.constant;

/**
 * 常量工具
 * @author TianLong Liu
 * @date 2019年8月30日 上午9:30:01
 */
public final class Constants {
	
	public static final String DEFAULT_CHARSET = "UTF-8";

	public static final class Headers{
		public static final String ACCESS_TOKEN = "access-token";
//		public static final String ACCESS_TOKEN = "access_token"; // _ 字符在 nginx代理的header中，默认会忽略掉
		public static final String LOGIN_USER_ID = "x-login-user-id";
		public static final String LOGIN_USER_NAME = "x-login-user-name";
		public static final String LOGIN_USER_INFO = "x-login-user-info";
	}
	
}
