package com.efe.ms.serviceconsumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.efe.ms.serviceconsumer.feign.InventoryServiceFeignClient;
import com.efe.ms.serviceconsumer.feign.OrderServiceFeignClient;

/**
 * 购物业务实现类
 * @author TianLong Liu
 * @date 2019年8月9日 上午9:31:30
 */
@Service
public class ShoppingServiceImpl implements ShoppingService {
	
	@Autowired
	private InventoryServiceFeignClient inventoryServiceFeignClient;
	
	@Autowired
	private OrderServiceFeignClient orderServiceFeignClient;

	@Override
	@LcnTransaction // 分布式事务注解
	@Transactional(rollbackFor=Exception.class)
	public String shopping(String userId, String sku, int count) {
		// 扣减库存
		inventoryServiceFeignClient.deduct(sku, count);
		// 生成订单，放到 inventory-service去调用，因service-consumer中只是业务协调，没有数据源，tx-lcn
		// 生成订单
		orderServiceFeignClient.create(userId, sku, count);
		return "success";
	}

}
