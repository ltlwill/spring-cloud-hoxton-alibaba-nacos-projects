package com.efe.ms.zuulgateway.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

/**
 * 自定义安全过滤器
 * @author TianLong Liu
 * @date 2019年9月10日 上午10:27:18
 */
public class ZuulGatewaySecurityFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response); 
		
	}

}
