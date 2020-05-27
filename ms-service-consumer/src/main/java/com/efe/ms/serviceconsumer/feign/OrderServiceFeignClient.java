package com.efe.ms.serviceconsumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Order Service服务调用的feign客户端
 * @author TianLong Liu
 * @date 2019年8月8日 下午5:57:50
 */
@FeignClient(value = "order-service")
public interface OrderServiceFeignClient {

	/**
	 * 创建订单
	 * @param userId
	 * @param sku
	 * @param count
	 * @return
	 */
	@GetMapping("/order/create")
	boolean create(@RequestParam("userId")String userId,@RequestParam("sku")String sku,@RequestParam("count")int count);
}
