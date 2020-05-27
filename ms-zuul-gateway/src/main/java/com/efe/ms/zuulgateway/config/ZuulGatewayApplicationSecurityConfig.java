package com.efe.ms.zuulgateway.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.efe.ms.zuulgateway.filter.ZuulGatewaySecurityFilter;
import com.efe.ms.zuulgateway.security.SecurityAuthenticationManager;
import com.efe.ms.zuulgateway.security.SecurityAuthenticationProvider;
import com.efe.ms.zuulgateway.security.SecurityUserDetailsService;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ZuulGatewayApplicationSecurityConfig extends
		WebSecurityConfigurerAdapter {
	
//	@Autowired
//	private SecurityAuthenticationProvider authenticationProvider;
	
//	@Autowired
//	private SecurityUserDetailsService securityUserDetailsService;
	
	@Bean("authenticationManager")
	public ProviderManager providerManager(){
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add(securityAuthenticationProvider());
//		providers.add(authenticationProvider);
		ProviderManager ma =  new ProviderManager(providers);
		ma.setEraseCredentialsAfterAuthentication(true);
		return ma;
	}
	
	/*@Bean
	public AuthenticationManager customAuthenticationManager(){
		return new SecurityAuthenticationManager();
	}*/
	
	@Bean
	public AuthenticationProvider securityAuthenticationProvider(){
		return new SecurityAuthenticationProvider(); 
	} 
	
	@Bean
	public UserDetailsService securityUserDetailsService(){
		return new SecurityUserDetailsService(); 
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 1.禁用 spring security
//		 super.configure(http); // Hoxton版会出现：Can't configure anyRequest after itself
		 http.csrf().disable() // 禁用CSRF
		 	.authorizeRequests().anyRequest().permitAll(); // 不起用security用户认证
		 
		// 2.启用 spring security (不知道为毛，自定义的AuthenticationManager、AuthenticationProvider、UserDetailsService认证逻辑始终不会执行,蛋疼)
//		http.csrf().disable().authorizeRequests()
//				.antMatchers("/login", "/signin", "/resources/**","/swagger-resources/**","/swagger-ui.html","/webjars/**","/**/v2/api-docs/**").permitAll()
//				.anyRequest().authenticated();
//		RequestMatcher s = new AntPathRequestMatcher("");
//		s.matcher(null).;
		
//		http.authenticationProvider(securityAuthenticationProvider())
//			.userDetailsService(securityUserDetailsService());
		// add filter
//		http.addFilterAt(new ZuulGatewaySecurityFilter(), AnonymousAuthenticationFilter.class);
	}
	
	/*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception{
//		authBuilder.authenticationProvider(authenticationProvider()).eraseCredentials(true); 
		authBuilder.authenticationProvider(authenticationProvider).eraseCredentials(true); 
//		authBuilder.userDetailsService(new SecurityUserDetailsService())
//                .passwordEncoder(passwordEncode());
    }*/
	
//	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(securityAuthenticationProvider());
        auth.userDetailsService(securityUserDetailsService()).passwordEncoder(passwordEncoder()); 
    }
	
}
