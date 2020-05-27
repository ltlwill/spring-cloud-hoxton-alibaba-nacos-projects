package com.efe.ms.serviceconsumer.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efe.ms.serviceconsumer.domain.Combo;
import com.efe.ms.serviceconsumer.domain.Product;
import com.efe.ms.serviceconsumer.feign.ProductServiceFeignClient;
import com.efe.ms.serviceconsumer.util.MapUtil;
import com.efe.ms.serviceconsumer.vo.Pagination;

/**
 * 
 * <p>服务消费业务接口实现类: </p> 
 * @author Liu TianLong
 * 2019年2月25日 下午3:20:22
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
	private ProductServiceFeignClient productServiceFeignClient;
	
	@Override
	public Pagination<Product> getProducts(Pagination<Product> page,
			Product product) throws Exception{
//		return productServiceFeignClient.getProductsByPage(page.getPageNo(),page.getPageSize(), product);
		Map<String,Object> params = MapUtil.beansToMap(page,product);
		return productServiceFeignClient.getProductsByPage(params);
	}

	@Override
	public Product getProductBySku(String sku) throws Exception {
		return productServiceFeignClient.getProductBySku(sku);
	}

	@Override
	public List<Combo> getComboListBySku(String sku) throws Exception {
		return productServiceFeignClient.getComboListBySku(sku);
	}

}
