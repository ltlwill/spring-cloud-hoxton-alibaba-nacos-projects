package com.efe.ms.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.efe.ms.common.interceptor.GlobalFeignRequestInterceptor;

/**
 * 
 * @author TianLong Liu
 * @date 2019年9月2日 上午9:17:02
 */

@Configuration
public class InterceptorConfiguration {
	
	/**
	 * Feign 拦截器
	 * @return
	 */
	@Bean
	public GlobalFeignRequestInterceptor feignRequestInterceptor() {
		return new GlobalFeignRequestInterceptor();
	}

}
