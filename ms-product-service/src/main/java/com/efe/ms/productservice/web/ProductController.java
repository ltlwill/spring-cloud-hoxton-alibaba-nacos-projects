package com.efe.ms.productservice.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.efe.ms.common.annotation.UnuseUnifiedResponse;
import com.efe.ms.common.domain.BusinessResult;
import com.efe.ms.common.domain.UserInfoDTO;
import com.efe.ms.common.util.WebRequestContextHolder;
import com.efe.ms.common.vo.ResponseResult;
import com.efe.ms.productservice.domain.Combo;
import com.efe.ms.productservice.domain.Product;
import com.efe.ms.productservice.service.ProductService;
import com.efe.ms.productservice.vo.Pagination;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * <p>
 * 产品控制器:
 * </p>
 * 
 * @author Liu TianLong 2018年10月9日 上午10:28:47
 */
@Api(tags = "产品API")
@RestController
//@UnuseUnifiedResponse // 不使用统一返回响应处理
@RequestMapping("/products")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	
	 /*@ApiOperation(value = "分页获取产品信息")
	 @GetMapping()
	 public Pagination<Product> getAllProducts(Pagination<Product> page, Product product) { 
		 return productService.getAllProducts(page, product); 
	 }*/
	
	@GetMapping(value="/name",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object name() {
		return "xxx";
	}

	@ApiOperation(value = "分页获取产品信息")
	@GetMapping
	public Pagination<Product> getAllProductsByPage(Integer pageNo,Integer pageSize,Product product) {
		return productService.getAllProductsByPage(pageNo, pageSize, product);
	}

	@ApiOperation(value = "根据SKU获取产品信息")
	@GetMapping("/{sku}")
	@UnuseUnifiedResponse // 不使用统一返回响应处理
	public Product getProductBySku(@PathVariable String sku) {
		UserInfoDTO user = WebRequestContextHolder.getUserInfo();
		System.out.println("--user--:" + (user == null ? null : JSON.toJSONString(user)));
		return productService.getProductBySku(sku); 
	}

	@ApiOperation(value = "根据SKU获取产品的组合信息")
	@GetMapping("/combo/{sku}")
	public List<Combo> getComboListBySku(@PathVariable String sku) {
		return productService.getComboListBySku(sku);
	}

	@ApiOperation(value = "根据SKU获取产品的组合信息中的所有SKU")
	@GetMapping("/combo/skus/{sku}")
	public List<String> getComboSkusBySku(@PathVariable String sku) {
		return productService.getComboSkusBySku(sku);
	}

	@ApiOperation(value = "根据SKU获取主SKU")
	@GetMapping("/combo/main/{sku}")
	public String getMainSku(@PathVariable String sku) {
		return productService.getMainSku(sku);
	}
}
