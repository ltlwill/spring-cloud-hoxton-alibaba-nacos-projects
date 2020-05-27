package com.efe.ms.crawlerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 应用配置
 *
 * @author TianLong Liu
 * @date 2019年11月6日 上午11:24:10
 */
@Configuration
public class ApplicationWebConfig extends WebMvcConfigurationSupport {
	
	@SuppressWarnings("unused")
	private static final String DEFAULT_CHARSET = "UTF-8";
	
}
