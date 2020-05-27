package com.efe.ms.serviceconsumer.service;

/**
 * 购物业务接口
 * @author TianLong Liu
 * @date 2019年8月9日 上午9:29:41
 */
public interface ShoppingService {
	
	/**
	 * 购物
	 * @param userId 用户ID
	 * @param sku  商品编码
	 * @param count 数量
	 * @return
	 */
	String shopping(String userId,String sku,int count);
}
