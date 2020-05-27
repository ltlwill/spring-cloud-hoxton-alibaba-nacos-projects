package com.efe.ms.zuulgateway.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efe.ms.common.constant.Constants;
import com.efe.ms.common.domain.BusinessResult;
import com.efe.ms.common.domain.UserInfoDTO;
import com.efe.ms.common.util.RequestMatcherUtil;
import com.efe.ms.common.util.UserInfoTransferUtil;
import com.efe.ms.zuulgateway.utils.RedisOperateUtil;

import io.lettuce.core.RedisConnectionException;

/**
 * 全局web过滤器，每次请求都设置传递当前的用户信息
 * 
 * @author liutianlong
 *
 */
@WebFilter(filterName="zuulGatewayWebFilter",urlPatterns="/*")
@Order(0)
public class ZuulGatewayWebFilter implements Filter {
	private static final Logger logger = LoggerFactory
			.getLogger(ZuulGatewayWebFilter.class);
	
	private static final String SWAGGER_UI_REFERER_END_STR = "/swagger-ui.html";
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		if(RequestMatcherUtil.isNoAuth(req) || isSwaggerReferer(req)) { // 无需认证
			chain.doFilter(req, res);
			return;
		}
		String accessToken = req.getHeader(Constants.Headers.ACCESS_TOKEN);
		if(StringUtils.isBlank(accessToken)) { //没有访问令牌
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
			res.getWriter().write(JSON.toJSONString(BusinessResult.fail("No access token")));
			return;
		}
		String userStr = getLoginInfoFromRedisWithRetry(accessToken,2);
		if(StringUtils.isBlank(userStr)) { // 没有登录信息
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write(JSON.toJSONString(BusinessResult.fail("Invalid access token")));
			return;
		}
		setTransferUserInfo(userStr);
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	private void setTransferUserInfo(String userStr){
		logger.info("---ZuulGatewayWebFilter.setTransferUserInfo---");
		try{
//			UserInfo user = new UserInfo();
//			user.setId("10001");
//			user.setName("test");
			UserInfoTransferUtil.setUserInfo(JSONObject.parseObject(userStr, UserInfoDTO.class));
		}catch(Exception e){
			logger.error("ZuulGatewayWebFilter 设置传递user信息失败",e); 
		}
		
	}
	
	private String getLoginInfoFromRedisWithRetry(String accessToken,int times){
		try {
			return getLoginInfoFromRedis(accessToken);
		}catch (RedisConnectionException e) {
			if(times < 0) {
				throw new RuntimeException(e);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				logger.error("thread sleep error",e);
			}
			return getLoginInfoFromRedisWithRetry(accessToken,times --);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getLoginInfoFromRedis(String accessToken) {
		return RedisOperateUtil.get(accessToken);
	}
	
	private boolean isSwaggerReferer(HttpServletRequest req) {
		String referer = req.getHeader("referer");
		String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + SWAGGER_UI_REFERER_END_STR;
		return StringUtils.isNotBlank(referer) && referer.startsWith(url);
	}	
}
