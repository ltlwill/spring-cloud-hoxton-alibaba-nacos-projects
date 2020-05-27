package com.efe.ms.serviceconsumer.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.efe.ms.serviceconsumer.domain.Combo;
import com.efe.ms.serviceconsumer.domain.Product;
import com.efe.ms.serviceconsumer.vo.Pagination;

/**
 * 
 * <p>Product Service服务调用的feign客户端: </p> 
 * @author Liu TianLong
 * 2019年2月26日 下午2:42:41
 */
//@FeignClient(value = "product-service",fallback=ProductServiceFeignClientFallback.class)
@FeignClient(value = "product-service")
public interface ProductServiceFeignClient {

	/**
	 * 
	 * <p>
	 * 注: 如果不加@RequestParam(""),feign会报Method has too many Body parameters的错误
	 * </p>
	 * 
	 * @param
	 * @author Liu TianLong
	 * @date 2019年2月25日 下午3:48:32
	 * @return Pagination<Product>
	 */
	/*@GetMapping("/products")
	Pagination<Product> getProductsByPage(
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("pageSize") Integer pageSize,@RequestParam("product") Product product);*/
	
	/**
	 * feign 的get方式不支持复杂的数据类型，如果服务端的get接口为复杂类型（如：一个实体类），可以在feign client请求时将参数设置为Map形式
	 * <p>Description: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2019年2月25日 下午5:33:06
	 * @return Pagination<Product>
	 */
	@GetMapping("/products")
	Pagination<Product> getProductsByPage(@RequestParam Map<String,Object> map);
	
	/**
	 * 
	 * <p>根据SKU获取产品信息: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2019年2月26日 下午2:43:02
	 * @return Product
	 */
	@GetMapping("/products/{sku}")
	Product getProductBySku(@PathVariable("sku") String sku);
	
	/**
	 * 
	 * <p>根据SKU获取此SKU的产品组合信息: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2019年2月26日 下午2:43:17
	 * @return List<Combo>
	 */
	@GetMapping("/products/combo/{sku}")
	List<Combo> getComboListBySku(@PathVariable("sku") String sku);
}
