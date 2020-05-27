package com.efe.ms.productservice.events;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * <p>Spring boot2 ApplicationStartingEvent (order 6),如果启动时发生异常，则发送ApplicationFailedEvent: </p> 
 * @author Liu TianLong
 * 2018年10月29日 上午11:25:51
 */
public class ProductApplicationFailedEventListener implements
		ApplicationListener<ApplicationFailedEvent> {

	@Override
	public void onApplicationEvent(ApplicationFailedEvent event) {
		System.out.println("-----ApplicationFailedEvent-----");
	}

}
