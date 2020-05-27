package com.efe.ms.crawlerservice.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.efe.ms.crawlerservice.vo.UserInfoVO;

/**
 * 请求工具类
 *
 * @author TianLong Liu
 * @date 2019年11月25日 上午11:20:33
 */
public class RequestUtil {

	public static UserInfoVO getUser() {
		String token = getHeader(Constant.HEADER_ACCESS_TOKEN);
		if (StringUtils.isBlank(token)) {
			return null;
		}
		return getUserInfo(RedisUtil.get(token));
	}
	
	public static UserInfoVO getUser(String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		return getUserInfo(RedisUtil.get(token));
	}

	public static UserInfoVO getUserInfo(String userJson) {
		return StringUtils.isBlank(userJson) ? null : JSON.toJavaObject(
				JSON.parseObject(userJson), UserInfoVO.class);
	}
	
	/**
	 * 得到request对象
	 */
	public static HttpServletRequest getHttpServletRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}
	public static String getHeader(String name) {
		HttpServletRequest request = getHttpServletRequest();
		return request == null ? null : request.getHeader(name);
	}
}
