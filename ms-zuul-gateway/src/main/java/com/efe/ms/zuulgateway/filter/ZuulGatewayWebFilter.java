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
import com.efe.ms.common.config.AppConfiguration;
import com.efe.ms.common.constant.Constants;
import com.efe.ms.common.domain.BusinessResult;
import com.efe.ms.common.domain.UserInfoDTO;
import com.efe.ms.common.util.RequestMatcherUtil;
import com.efe.ms.common.util.SpringBeanUtil;
import com.efe.ms.common.util.WebRequestContextHolder;
import com.efe.ms.zuulgateway.utils.RedisOperateUtil;

import io.lettuce.core.RedisConnectionException;

/**
 * 全局web过滤器，每次请求都设置传递当前的用户信息
 * 
 * @author liutianlong
 *
 */
@WebFilter(filterName = "zuulGatewayWebFilter", urlPatterns = "/*")
@Order(1)
public class ZuulGatewayWebFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(ZuulGatewayWebFilter.class);

	private static final String SWAGGER_UI_REFERER_END_STR = "/swagger-ui.html";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		AppConfiguration appCfg = SpringBeanUtil.getBean(AppConfiguration.class);
		// 无需认证，swagger-ui.html里面的ajax请求在开启swagger文档时不需要拦截
		if (RequestMatcherUtil.isNoAuth(req) || (appCfg.isSwagger2Enabled() && isSwaggerReferer(req))) {
			chain.doFilter(req, res);
			return;
		}
		String accessToken = req.getHeader(Constants.Headers.ACCESS_TOKEN);
		String userStr = getLoginInfoFromRedisWithRetry(accessToken, 2);
		if (appCfg.isVerifyTokenEnabled() && StringUtils.isBlank(userStr)) { // 没有登录信息
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write(JSON.toJSONString(
					BusinessResult.fail(BusinessResult.ResultCode.INVALID_TOKEN, "User is not logged in")));
			return;
		}
		setTransferUserInfo(userStr, accessToken);
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private void setTransferUserInfo(String userStr, String accessToken) {
		logger.info("---ZuulGatewayWebFilter.setTransferUserInfo---");
		try {
			UserInfoDTO userInfo = JSONObject.parseObject(userStr, UserInfoDTO.class);
			userInfo.setAccessToken(accessToken);
			WebRequestContextHolder.setUserInfo(userInfo);
		} catch (Exception e) {
			logger.error("ZuulGatewayWebFilter 设置传递user信息失败", e);
		}

	}

	private String getLoginInfoFromRedisWithRetry(String accessToken, int times) {
		try {
			if (StringUtils.isBlank(accessToken))
				return null;
			return getLoginInfoFromRedis(accessToken);
		} catch (RedisConnectionException e) {
			if (times < 0) {
				throw new RuntimeException(e);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				logger.error("thread sleep error", e);
			}
			return getLoginInfoFromRedisWithRetry(accessToken, times--);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getLoginInfoFromRedis(String accessToken) {
		return RedisOperateUtil.get(accessToken);
	}

	private boolean isSwaggerReferer(HttpServletRequest req) {
		String referer = req.getHeader("referer");
		String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
				+ SWAGGER_UI_REFERER_END_STR;
		return StringUtils.isNotBlank(referer) && referer.startsWith(url);
	}
}
