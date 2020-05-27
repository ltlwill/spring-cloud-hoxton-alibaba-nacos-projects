package com.efe.ms.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {
	
	private List<String> noAuthPatterns;
	
	private List<RequestMatcher> __matchers;

	
	public List<String> getNoAuthPatterns() {
		return noAuthPatterns;
	}

	public void setNoAuthPatterns(List<String> noAuthPatterns) {
		this.noAuthPatterns = noAuthPatterns;
		this.refreshMatchers();
	}

	public List<RequestMatcher> getMatchers() {
		return __matchers;
	}

	private void refreshMatchers() {
		if(this.noAuthPatterns == null || this.noAuthPatterns.isEmpty()) {
			this.__matchers = null;
			return;
		}
		this.__matchers = new ArrayList<RequestMatcher>();
		this.noAuthPatterns.forEach(url -> __matchers.add(new AntPathRequestMatcher(url)));
	}

}


