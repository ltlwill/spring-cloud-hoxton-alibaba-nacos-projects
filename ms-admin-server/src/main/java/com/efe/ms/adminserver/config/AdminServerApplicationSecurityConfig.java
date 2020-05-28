package com.efe.ms.adminserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

@EnableWebSecurity
@Configuration
public class AdminServerApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final String contextPath;

	public AdminServerApplicationSecurityConfig(AdminServerProperties adminServerProperties) {
		contextPath = adminServerProperties.getContextPath();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");

		http.authorizeRequests().antMatchers(contextPath + "/assets/**").permitAll().antMatchers(contextPath + "/login")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage(contextPath + "/login")
				.successHandler(successHandler).and().logout().logoutUrl(contextPath + "/logout").and().httpBasic()
				.and().csrf().disable();
	}
}
