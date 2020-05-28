package com.efe.ms.zuulgateway.filter;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.efe.ms.common.constant.Constants;
import com.efe.ms.common.domain.UserInfoDTO;
import com.efe.ms.common.util.UserInfoTransferUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * zuul网关前置过滤器，增加传递当前登录用户的信息
 * 
 * @author TianLong Liu
 * @date 2019年8月30日 下午4:52:36
 */
@Component
public class UserInfoPreSettingFilter extends ZuulFilter {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoPreSettingFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		setTransferUserInfo();
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	private void setTransferUserInfo() {
		logger.error("UserInfoPreSettingFilter setTransferUserInfo...");
		try {
			RequestContext context = RequestContext.getCurrentContext();
			/*
			 * HttpServletRequest request = context.getRequest(); String token =
			 * request.getHeader(Constants.Headers.ACCESS_TOKEN); String userId =
			 * request.getHeader(Constants.Headers.TANSFER_USER_ID); UserInfo user = new
			 * UserInfo(); user.setId("10001"); user.setName("test");
			 * UserInfoUtil.setUserInfo(user);
			 */
			UserInfoDTO user = UserInfoTransferUtil.getUserInfo();
			context.addZuulRequestHeader(Constants.Headers.LOGIN_USER_INFO,
					user == null ? null : URLEncoder.encode(JSON.toJSONString(user), Constants.DEFAULT_CHARSET));
		} catch (Exception e) {
			logger.error("UserInfoPreSettingFilter 设置传递user信息失败", e);
		}

	}

}
