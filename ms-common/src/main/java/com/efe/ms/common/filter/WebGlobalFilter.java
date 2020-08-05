package com.efe.ms.common.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.efe.ms.common.config.AppConfiguration;
import com.efe.ms.common.constant.Constants;
import com.efe.ms.common.domain.BusinessResult;
import com.efe.ms.common.domain.UserInfoDTO;
import com.efe.ms.common.util.JWTUtil;
import com.efe.ms.common.util.RequestMatcherUtil;
import com.efe.ms.common.util.SpringBeanUtil;
import com.efe.ms.common.util.WebRequestContextHolder;

/**
 * 全局web过滤器，每次请求都设置传递当前的用户信息
 * 
 * @author liutianlong
 *
 */
public class WebGlobalFilter implements Filter {
	private final static Logger logger = LoggerFactory.getLogger(WebGlobalFilter.class);
	private static final String SWAGGER_UI_REFERER_END_STR = "/swagger-ui.html";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("自定义filter doFilter...");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		AppConfiguration appCfg = SpringBeanUtil.getBean(AppConfiguration.class);
		// 无需认证，swagger-ui.html里面的ajax请求在开启swagger文档时不需要拦截
		if (RequestMatcherUtil.isNoAuth(req) || (appCfg.isSwagger2Enabled() && isSwaggerReferer(req))) {
			chain.doFilter(req, res);
			return;
		}
		String accessToken = req.getHeader(Constants.Headers.ACCESS_TOKEN);
		if (appCfg.isVerifyTokenEnabled() && !JWTUtil.verify(appCfg == null ? null : appCfg.getJwt(), accessToken)) { // 无效 令牌
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write(JSON.toJSONString(
					BusinessResult.fail(BusinessResult.ResultCode.INVALID_TOKEN, "Invalid access token")));
			return;
		}
		setTransferUserInfo((HttpServletRequest) request);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private void setTransferUserInfo(ServletRequest request) {
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String userInfoStr = httpRequest.getHeader(Constants.Headers.LOGIN_USER_INFO);
			if (StringUtils.isNotBlank(userInfoStr)) {
				UserInfoDTO userInfo = JSON.parseObject(URLDecoder.decode(userInfoStr, Constants.DEFAULT_CHARSET),
						UserInfoDTO.class);
				WebRequestContextHolder.setUserInfo(userInfo);
			}
		} catch (Exception e) {
			logger.error("WebGlobalFilter 设置传递user信息失败", e);
		}

	}

	private boolean isSwaggerReferer(HttpServletRequest req) {
		String referer = req.getHeader("referer");
		String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
				+ SWAGGER_UI_REFERER_END_STR;
		return StringUtils.isNotBlank(referer) && referer.startsWith(url);
	}

}
