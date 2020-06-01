package com.efe.ms.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.efe.ms.common.constant.Constants;
import com.efe.ms.common.domain.UserInfoDTO;
import com.efe.ms.common.util.WebRequestContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 全局Feign请求过滤器，请求时自动添加用户信息消息头
 * @author TianLong Liu
 * @date 2019年8月30日 上午10:06:33
 */
public class GlobalFeignRequestInterceptor implements RequestInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(GlobalFeignRequestInterceptor.class);

	@Override
	public void apply(RequestTemplate template) {
		setTransferUserInfo(template);
	}

	private void setTransferUserInfo(RequestTemplate template) {
		logger.info("---FeignRequestInterceptor.setTransferUserInfo---");
		try {
			UserInfoDTO userInfo = WebRequestContextHolder.getUserInfo();
			if (userInfo != null) {
				template.header(Constants.Headers.LOGIN_USER_INFO,
						JSON.toJSONString(userInfo));
			}
		} catch (Exception e) {
			logger.error("FeignRequestInterceptor 设置用户信息失败", e);
		}
	}

}
