package com.efe.ms.exampleservice.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageInterceptor;

import lombok.Setter;

@Setter
@Configuration
@ConfigurationProperties(prefix = "mybatis")
public class MybatisConfig {

	private Map<String, String> configurationProperties;

	/**
	 * 
	 * <p>
	 * java bean方式分页插件配置:
	 * </p>
	 * 
	 * @param
	 * @author Liu TianLong
	 * @date 2019年5月13日 下午2:47:03
	 * @return PageInterceptor
	 */
	@Bean
	public PageInterceptor pageInterceptor() {
		PageInterceptor pageInterceptor = new PageInterceptor();
		Properties props = new Properties();
		props.putAll(configurationProperties);
		pageInterceptor.setProperties(props);
		return pageInterceptor;
	}

}
