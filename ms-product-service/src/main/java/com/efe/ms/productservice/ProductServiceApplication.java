package com.efe.ms.productservice;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.efe.ms.productservice.events.ProductApplicationStartingEventListener;

/**
 * 
 * <p>
 * 产品服务:
 * </p>
 * 
 * @author Liu TianLong 2018年8月24日 上午11:42:30
 */
@SpringCloudApplication
@ComponentScan(basePackages="com.efe.ms") // 需要包含ms-common项目下的，否则扫描不到ms-common项目下的bean信息
public class ProductServiceApplication {
	public static void main(String[] args) {
		// SpringApplication.run(ProductServiceApplication.class, args);
		SpringApplication app = new SpringApplication(
				ProductServiceApplication.class);
		app.addListeners(new ProductApplicationStartingEventListener()); // 注册一个监听器
		app.addListeners((ApplicationReadyEvent event) -> System.out
				.println("=====ApplicationReadyEvent=====")); // lamda表达式
		app.run(args);
	}

	@Bean
	public ExitCodeGenerator exitCodeGenerator() {
		return () -> 110; // 自定义停机码
	}
}
