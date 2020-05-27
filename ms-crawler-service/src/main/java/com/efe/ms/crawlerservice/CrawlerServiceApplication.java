package com.efe.ms.crawlerservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 *  爬虫服务应用启动入口
 * @author TianLong Liu
 * @date 2019年11月6日 下午4:21:09
 */

//@SpringBootApplication
@SpringCloudApplication
@EnableTransactionManagement
@MapperScan("com.efe.ms.**.dao")
@ComponentScan(basePackages="com.efe.ms")
@EnableScheduling
@ServletComponentScan
public class CrawlerServiceApplication {

	public static void main(String[] args) { 
		SpringApplication.run(CrawlerServiceApplication.class, args);
	}

}
