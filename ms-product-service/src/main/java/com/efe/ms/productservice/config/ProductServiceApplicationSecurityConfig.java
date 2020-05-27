package com.efe.ms.productservice.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ProductServiceApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http); // spring cloud hoxton后会报： Can't configure anyRequest after itself 的错误，导致应用启动失败
		http.csrf().disable() // 禁用CSRF
			.authorizeRequests().anyRequest().permitAll(); // 不起用security用户认证
	}
}
