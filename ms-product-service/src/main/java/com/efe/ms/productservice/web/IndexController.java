package com.efe.ms.productservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {
	
	@Value("${server.port:}")
	private String serverPort;
	
	@Value("${common.test:}")
	private String configStr;
	
	@RequestMapping("/server/port")
	public String serverPort(){
		return serverPort;
	}
	
	@GetMapping("config")
	public String getCommonConfig() {
		return configStr;
	}

}
