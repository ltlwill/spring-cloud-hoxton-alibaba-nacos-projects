package com.efe.ms.productservice.events;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * <p>Spring boot2 ApplicationStartingEvent (order 3),在刷新开始之前但在加载bean定义之后发送: </p> 
 * @author Liu TianLong
 * 2018年10月29日 上午11:21:19
 */
public class ProductApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		System.out.println("-----ProductApplicationPreparedEvent-----");
	}

}
