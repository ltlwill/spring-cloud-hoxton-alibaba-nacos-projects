package com.efe.ms.serviceconsumer.request;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义监听器（需要在应用入口application类上添加“@ServletComponentScan”，才能生效）
 * @author liutianlong
 *
 */
@WebListener
public class ApplicationListener implements ServletContextListener
{
	private final static Logger logger = LoggerFactory.getLogger(ApplicationFilter.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent servletcontextevent)
	{

	}

	@Override
	public void contextInitialized(ServletContextEvent servletcontextevent)
	{
		logger.debug("自定义监听器初始化...");
	}

}
