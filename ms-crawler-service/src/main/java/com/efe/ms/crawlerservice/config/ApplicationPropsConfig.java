package com.efe.ms.crawlerservice.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationPropsConfig {
	
	// 是否启用代理池
	private boolean enableProxyPool;
	// 代理服务器
	private List<Map<String,String>> proxyPools; 
}
