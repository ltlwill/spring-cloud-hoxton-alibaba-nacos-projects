package com.efe.ms.productservice.events;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * <p>Spring boot2 ApplicationStartingEvent (order 1),在运行开始时但在任何处理之前发送，但监听器和初始化程序的注册除外: </p> 
 * @author Liu TianLong
 * 2018年10月29日 上午11:17:58
 */
public class ProductApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent>{

	@Override
	public void onApplicationEvent(ApplicationStartingEvent event) {
		System.out.println("-----ApplicationStartingEvent-----");
	}

}
