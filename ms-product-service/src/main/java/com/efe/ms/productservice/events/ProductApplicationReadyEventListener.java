package com.efe.ms.productservice.events;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * <p>Spring boot2 ApplicationStartingEvent (order 5), 在调用任何应用程序和命令行运行程序之后发送ApplicationReadyEvent。 它表示应用程序已准备好为请求提供服务: </p> 
 * @author Liu TianLong
 * 2018年10月29日 上午11:24:49
 */
public class ProductApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("-----ApplicationReadyEvent-----");
	}

}
