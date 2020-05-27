package com.efe.ms.productservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {
	
	@Value("${server.port:}")
	private String serverPort;
	
	@RequestMapping("/server/port")
	public String serverPort(){
		return serverPort;
	}

}
