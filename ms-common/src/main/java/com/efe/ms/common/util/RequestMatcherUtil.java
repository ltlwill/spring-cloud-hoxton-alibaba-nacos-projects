package com.efe.ms.common.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

import com.efe.ms.common.config.AppConfiguration;

public class RequestMatcherUtil {
	
	private static AppConfiguration cfg;
	
	/**
	 * 判断是否是无需认证URL
	 * @param request
	 * @return
	 */
	public static boolean isNoAuth(HttpServletRequest request) {
		List<RequestMatcher> matchers = getAppConfiguration().getMatchers();
		List<String> patterns = getAppConfiguration().getNoAuthPatterns();
		if(matchers == null || matchers.isEmpty()) {
			return false;
		}
		String url = request.getServletPath();
		if(url == null || "".equals(url.trim()) || "/".equals(url.trim())) {
			return true;
		}
		int len = matchers.size();
		for (int i = 0; i < len; i++) {
			if(patterns.get(i).equals(url) || matchers.get(i).matches(request)) {
				return true;
			}
		}
		return false;
	}
	
	
	private static AppConfiguration getAppConfiguration() {
		if(cfg == null) {
			synchronized (RequestMatcherUtil.class) {
				if(cfg == null) {
					cfg = SpringBeanUtil.getBean(AppConfiguration.class);
				}
			}
		}
		return cfg;
	}

}
