package com.efe.ms.productservice.events;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * <p>Spring boot2 ApplicationEnvironmentPreparedEvent (order 2),当要在上下文中使用的环境已知但在创建上下文之前</p> 
 * @author Liu TianLong
 * 2018年10月29日 上午11:19:34
 */
public class ProductApplicationEnvironmentPreparedEventListener implements
		ApplicationListener<ApplicationEnvironmentPreparedEvent> {

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		System.out.println("-----ApplicationEnvironmentPreparedEvent-----");
	}

}
