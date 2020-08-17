package com.efe.ms.common.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.efe.ms.common.config.AppConfiguration;
import com.efe.ms.common.util.SpringBeanUtil;

/**
 * 防止XSS攻击的过滤器
 * 
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (handleExcludeURL(req, resp)) {
			chain.doFilter(request, response);
			return;
		}
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
		AppConfiguration appCfg = SpringBeanUtil.getBean(AppConfiguration.class);
		if (!appCfg.isXssEnabled()) {
			return true;
		}
		if (appCfg.getXssExcludeUrls() == null || appCfg.getXssExcludeUrls().isEmpty()) {
			return false;
		}
		String url = request.getServletPath();
		for (String pattern : appCfg.getXssExcludeUrls()) {
			Pattern p = Pattern.compile("^" + pattern);
			Matcher m = p.matcher(url);
			if (m.find()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {

	}
}