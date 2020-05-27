package com.efe.ms.serviceconsumer.service;

import java.util.List;

import com.efe.ms.serviceconsumer.domain.Combo;
import com.efe.ms.serviceconsumer.domain.Product;
import com.efe.ms.serviceconsumer.vo.Pagination;

/**
 * 
 * <p>服务消费业务接口: </p> 
 * @author Liu TianLong
 * 2019年2月25日 下午3:15:54
 */
public interface ConsumerService {

	/**
	 * 
	 * <p>根据条件分页获取产品信息: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2019年2月25日 下午3:17:16
	 * @return Pagination<Product>
	 */
	Pagination<Product> getProducts(Pagination<Product> page,Product product) throws Exception;
	
	/**
	 * 
	 * <p>根据SKU获取产品信息: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2019年2月26日 上午9:22:47
	 * @return Product
	 */
	Product getProductBySku(String sku) throws Exception;
	
	/**
	 * 
	 * <p>根据SKU获取组合信息: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2019年2月26日 上午9:37:43
	 * @return List<Combo>
	 */
	List<Combo> getComboListBySku(String sku) throws Exception;
	
}
