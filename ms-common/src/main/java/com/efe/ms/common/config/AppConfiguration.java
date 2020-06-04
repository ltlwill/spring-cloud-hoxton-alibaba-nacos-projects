package com.efe.ms.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {
	
	private JWTConfig jwt;
	
	private List<String> noAuthPatterns;
	
	private List<RequestMatcher> __matchers;
	
	private boolean swagger2Enabled;
	
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

	private void refreshMatchers() {
		if(this.noAuthPatterns == null || this.noAuthPatterns.isEmpty()) {
			this.__matchers = null;
			return;
		}
		this.__matchers = new ArrayList<RequestMatcher>();
		this.noAuthPatterns.forEach(url -> __matchers.add(new AntPathRequestMatcher(url)));
	}
	
	
	@Setter
	@Getter
	@NoArgsConstructor
	public static class JWTConfig{
		private String secret; // 密匙
		private String iss; // jwt签发者
		private String sub; // jwt所面向的用户
		private String aud; // 接收jwt的一方
		private Long exp;   // jwt的过期时间，这个过期时间必须要大于签发时间(毫秒)
		private String nbf; // 定义在什么时间之前，该jwt都是不可用的
		private String iat; // jwt的签发时间
		private String jti; // jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
	}

}


