package com.efe.ms.serviceconsumer.request;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义过滤器（需要在应用入口application类上添加“@ServletComponentScan”，才能生效）
 * 
 * @author liutianlong
 *
 */
@WebFilter(filterName="myFilter",urlPatterns="/myFilter/*")
public class ApplicationFilter implements Filter
{
	private final static Logger logger = LoggerFactory.getLogger(ApplicationFilter.class);
	
	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException
	{
		logger.debug("自定义filter doFilter...");
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}

}
