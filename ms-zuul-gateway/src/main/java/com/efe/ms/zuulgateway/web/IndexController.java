package com.efe.ms.zuulgateway.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.common.config.AppConfiguration;
import com.efe.ms.zuulgateway.utils.JWTUtil;

/**
 * 
 * @author TianLong Liu
 * @date 2019年8月30日 下午5:52:02
 */
@RefreshScope
@RestController
public class IndexController {
	
	@Value("${app.welcome: welcome to zuul gateway}")
	private String welcomeStr;
	
	@Autowired
	private AppConfiguration cfg;
	
	@RequestMapping()
	public String index(){
		return welcomeStr;
	}
	
	@RequestMapping("/login")
	public String login(String userId){
		if(StringUtils.isBlank(userId)){
			throw new RuntimeException();
		}
		return JWTUtil.createToken(cfg.getJwt(),userId,userId);
	}
	
	@RequestMapping("verifyToken")
	public String verifyToken(String userId,String token){
		if(StringUtils.isBlank(token)) return "false";
		return String.valueOf(JWTUtil.verify(cfg.getJwt(),token));
	}

}
