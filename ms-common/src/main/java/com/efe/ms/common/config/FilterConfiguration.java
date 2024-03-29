package com.efe.ms.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.efe.ms.common.filter.WebGlobalFilter;
import com.efe.ms.common.filter.XssFilter;

/**
 * 
 * @author TianLong Liu
 * @date 2019年8月30日 下午4:11:22
 */

@Configuration
public class FilterConfiguration {
	
	private static final int DEFAULT_FILTER_ORDER = 0;
	private static final int DEFAULT_FILTER_HIGH_ORDER = 5;
	
	@Bean
    public FilterRegistrationBean<WebGlobalFilter> webGlobalFilter(){
        FilterRegistrationBean<WebGlobalFilter> bean = new FilterRegistrationBean<WebGlobalFilter>();
        bean.setFilter(new WebGlobalFilter());
        bean.addUrlPatterns("/*"); // 使用"/**"的方式无效，匹配不了
        bean.setOrder(DEFAULT_FILTER_ORDER);
        return bean;
    }
	
	@Bean
	public FilterRegistrationBean<XssFilter> xssFilter(){
		FilterRegistrationBean<XssFilter> bean = new FilterRegistrationBean<XssFilter>();
		bean.setFilter(new XssFilter());
		bean.addUrlPatterns("/*");
		bean.setOrder(DEFAULT_FILTER_HIGH_ORDER);
		return bean;
	}
}
