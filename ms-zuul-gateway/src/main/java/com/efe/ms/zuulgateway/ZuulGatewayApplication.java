package com.efe.ms.zuulgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * 服务网关(使用netflix的zuul框架实现)
 * 
 * @author Tianlong Liu
 * @2020年5月22日 下午3:29:33
 */
@EnableZuulProxy
@SpringCloudApplication
@ComponentScan(basePackages = "com.efe.ms") // 需要包含ms-common项目下的，否则扫描不到ms-common项目下的bean信息
@ServletComponentScan
public class ZuulGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZuulGatewayApplication.class, args);
	}
}
