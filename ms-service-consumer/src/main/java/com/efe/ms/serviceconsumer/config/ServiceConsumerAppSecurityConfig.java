package com.efe.ms.serviceconsumer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 
 * <p>Spring security 配置: </p> 
 * @author Liu TianLong
 * 2019年2月19日 上午10:55:30
 */
@Configuration
@EnableWebSecurity
public class ServiceConsumerAppSecurityConfig extends
		WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
	} 
}
