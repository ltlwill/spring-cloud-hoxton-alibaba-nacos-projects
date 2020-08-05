package com.efe.ms.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.efe.ms.common.util.JWTUtil.JWTConfig;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {
	
	private JWTConfig jwt;
	
	private List<String> noAuthPatterns;
	
	private List<RequestMatcher> __matchers;
	
	private boolean swagger2Enabled;
	
	private boolean verifyTokenEnabled = true;
	
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

	public JWTConfig getJwt() {
		return jwt;
	}

	public void setJwt(JWTConfig jwt) {
		this.jwt = jwt;
	}

	public boolean isSwagger2Enabled() {
		return swagger2Enabled;
	}

	public void setSwagger2Enabled(boolean swagger2Enabled) {
		this.swagger2Enabled = swagger2Enabled;
	}

	public boolean isVerifyTokenEnabled() {
		return verifyTokenEnabled;
	}

	public void setVerifyTokenEnabled(boolean verifyTokenEnabled) {
		this.verifyTokenEnabled = verifyTokenEnabled;
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


