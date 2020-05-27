package com.efe.ms.serviceconsumer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.serviceconsumer.service.ShoppingService;

/**
 * 购物业务控制器（测试分布式事务，业务服务由：inventory-service,order-service,account-service模拟）
 * @author TianLong Liu
 * @date 2019年8月9日 上午9:25:52
 */
@RestController
@RequestMapping("/shopping")
public class ShoppingController {
	
	@Autowired
	private ShoppingService shoppingService;
	
	@RequestMapping
	public String shopping(String userId,String sku,int count){
		return shoppingService.shopping(userId, sku, count);
	}

}
