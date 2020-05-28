package com.efe.ms.adminserver;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * 
 * <p>
 * Spring boot 监控服务端(spring boot admin server):
 * </p>
 * 
 * @author Liu TianLong 2018年9月18日 上午10:02:44
 */
@EnableAdminServer
@SpringCloudApplication
public class AdminServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplication.class, args);
	}
}
