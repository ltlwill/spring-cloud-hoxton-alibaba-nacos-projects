package com.efe.ms.serviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * <p>
 * 服务消费应用:
 * </p>
 * 
 * @author Liu TianLong 2019年2月19日 上午10:39:23
 */
@EnableFeignClients
@SpringCloudApplication
// 方式1：使用@SpringCloudApplication注解，此服务不需要数据源，排除掉，否则启动失败
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class,H2ConsoleAutoConfiguration.class})

// 方式二：不使用@SpringCloudApplication注解
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
//@EnableDiscoveryClient
//@EnableCircuitBreaker

//@EnableDistributedTransaction // 开启tx-lcn分布式事务控制
@ComponentScan(basePackages="com.efe.ms") // 需要包含ms-common项目下的，否则扫描不到ms-common项目下的bean信息
public class ServiceConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceConsumerApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
