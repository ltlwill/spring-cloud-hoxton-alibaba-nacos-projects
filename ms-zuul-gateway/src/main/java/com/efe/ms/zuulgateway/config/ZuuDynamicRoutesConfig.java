package com.efe.ms.zuulgateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 根据配置中心动态刷新zuul路由配置
 * @author Tianlong Liu
 * @2020年6月1日 下午3:20:17
 */
@Configuration
public class ZuuDynamicRoutesConfig {
	
    @RefreshScope
    @ConfigurationProperties("zuul")
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }

}
