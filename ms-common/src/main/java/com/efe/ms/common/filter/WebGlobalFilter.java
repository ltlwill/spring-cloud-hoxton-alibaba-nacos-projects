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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.efe.ms.common.constant.Constants;
import com.efe.ms.common.domain.UserInfoDTO;
import com.efe.ms.common.util.UserInfoTransferUtil;

/**
 * 全局web过滤器，每次请求都设置传递当前的用户信息
 * 
 * @author liutianlong
 *
 */
public class WebGlobalFilter implements Filter {
	private final static Logger logger = LoggerFactory.getLogger(WebGlobalFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("自定义filter doFilter...");
		setTransferUserInfo((HttpServletRequest) request);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private void setTransferUserInfo(ServletRequest request) {
//		logger.info("---WebGlobalFilter.setTransferUserInfo---");
		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String userInfoStr = httpRequest.getHeader(Constants.Headers.LOGIN_USER_INFO);
			if (StringUtils.isNotBlank(userInfoStr)) {
				UserInfoDTO userInfo = JSON.parseObject(URLDecoder.decode(userInfoStr, Constants.DEFAULT_CHARSET), 
						UserInfoDTO.class);
				UserInfoTransferUtil.setUserInfo(userInfo);
			}
		} catch (Exception e) {
			logger.error("WebGlobalFilter 设置传递user信息失败", e);
		}

	}

}
