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
	
	// JWT相关配置
	private JWTConfig jwt;
	// 无需认证的url
	private List<String> noAuthPatterns;
	private List<RequestMatcher> __matchers;
	// 是否启用swagger文档
	private boolean swagger2Enabled;
	// 是否启用验证token
	private boolean verifyTokenEnabled = true;
	//是否启用统一响应处理
	private boolean unifiedResponseEnabled = true;
	// 是否启用防范XSS攻击
	private boolean xssEnabled = true;
	// 防范XSS攻击时排除的URL
	private List<String> xssExcludeUrls;
	
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

	public boolean isUnifiedResponseEnabled() {
		return unifiedResponseEnabled;
	}

	public void setUnifiedResponseEnabled(boolean unifiedResponseEnabled) {
		this.unifiedResponseEnabled = unifiedResponseEnabled;
	}

	public boolean isXssEnabled() {
		return xssEnabled;
	}

	public void setXssEnabled(boolean xssEnabled) {
		this.xssEnabled = xssEnabled;
	}

	public List<String> getXssExcludeUrls() {
		return xssExcludeUrls;
	}

	public void setXssExcludeUrls(List<String> xssExcludeUrls) {
		this.xssExcludeUrls = xssExcludeUrls;
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


