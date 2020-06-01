package com.efe.ms.serviceconsumer.request;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义session监听器（需要在应用入口application类上添加“@ServletComponentScan”，才能生效）
 * 
 * @author liutianlong
 *
 */
@WebListener
public class ApplicationSessionListener implements HttpSessionListener {
	private final static Logger logger = LoggerFactory.getLogger(ApplicationSessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent httpsessionevent) {
		logger.debug("自定义session监听器 session创建...");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpsessionevent) {
		logger.debug("自定义session监听器 session销毁...");
	}

}
