package com.efe.ms.productservice.events;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * <p>Spring boot2 ApplicationStartingEvent (order 4),在刷新上下文之后但在调用任何应用程序和命令行运行程序之前: </p> 
 * @author Liu TianLong
 * 2018年10月29日 上午11:23:12
 */
public class ProductApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) { 
		System.out.println("-----ApplicationStartedEvent-----");
	}

}
