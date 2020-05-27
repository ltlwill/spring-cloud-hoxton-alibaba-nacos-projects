package com.efe.ms.productservice.service;

import java.util.List;

import com.efe.ms.productservice.domain.Combo;
import com.efe.ms.productservice.domain.Product;
import com.efe.ms.productservice.vo.Pagination;


/**
 * 
 * <p>产品(Product)业务接口: </p> 
 * @author Liu TianLong
 * 2018年8月24日 下午5:51:44
 */
public interface ProductService {
	
	/**
	 * 
	 * <p>根据SKU获取产品信息: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2018年10月9日 上午10:30:18
	 * @return Product
	 */
	Product getProductBySku(String sku);
	
	/**
	 * 
	 * <p>根据多个SKU批量查询产品信息: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2018年10月16日 下午3:16:27
	 * @return Product
	 */
	List<Product> getProductsBySkus(List<String> skus);
	
	/**
	 * 分页获取产品数据
	 * <p>Description: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2018年10月16日 下午2:20:40
	 * @return PageVO<Product>
	 */
	Pagination<Product> getAllProducts(Pagination<Product> page,Product product);
	
	/**
	 * 
	 * <p>分页获取产品数据: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2019年2月25日 下午4:12:57
	 * @return Pagination<Product>
	 */
	Pagination<Product> getAllProductsByPage(Integer pageNo,Integer pageSize,Product product);
	
	/**
	 * 
	 * <p>根据SKU获取combo组合信息: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2018年10月16日 下午2:20:35
	 * @return List<Combo>
	 */
	List<Combo> getComboListBySku(String sku);
	
	/**
	 * 
	 * <p>根据SKU获取组合信息中的所有SKU: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2018年10月16日 下午5:25:33
	 * @return List<String>
	 */
	List<String> getComboSkusBySku(String sku);
	
	/**
	 * 
	 * <p>根据SKU获取主SKU: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2018年10月16日 下午5:40:38
	 * @return String
	 */
	String getMainSku(String sku);
	
}
