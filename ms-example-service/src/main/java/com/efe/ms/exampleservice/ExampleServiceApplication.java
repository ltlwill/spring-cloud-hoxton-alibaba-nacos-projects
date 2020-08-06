package com.efe.ms.exampleservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * 
 *  服务案列
 * @author Liu TianLong 2019年2月19日 上午10:39:23
 */
@EnableFeignClients
@SpringCloudApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class,H2ConsoleAutoConfiguration.class})
@EnableTransactionManagement
@MapperScan("com.efe.ms.**.dao")
@ComponentScan(basePackages="com.efe.ms")
@EnableScheduling
@ServletComponentScan
public class ExampleServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExampleServiceApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
