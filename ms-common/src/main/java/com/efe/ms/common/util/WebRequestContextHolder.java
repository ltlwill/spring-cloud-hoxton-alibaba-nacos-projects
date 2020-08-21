package com.efe.ms.common.util;

import com.efe.ms.common.domain.UserInfoDTO;

/**
 * 
 * @author TianLong Liu
 * @date 2019年8月30日 上午9:34:41
 */
public final class WebRequestContextHolder {
	
	private static final ThreadLocal<UserInfoDTO> userInfoHolder = new InheritableThreadLocal<>();
	private static final ThreadLocal<String> userIdHolder = new InheritableThreadLocal<>();
	
	public static UserInfoDTO getUserInfo(){
		return userInfoHolder.get();
	}
	
	public static void setUserInfo(UserInfoDTO userInfo){
		userInfoHolder.set(userInfo);
	}
	
	public static void removeUserInfo() {
		userInfoHolder.remove();
	}
	
	public static String getUserId(){
		return userIdHolder.get();
	}
	
	public static void setUserId(String userId){
		userIdHolder.set(userId);
	}
	public static void removeUserId(String userId){
		userIdHolder.remove();
	}

}
