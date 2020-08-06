package com.efe.ms.exampleservice.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.exampleservice.model.biz.Combo;
import com.efe.ms.exampleservice.model.biz.Product;
import com.efe.ms.exampleservice.service.ConsumerService;
import com.efe.ms.exampleservice.vo.Pagination;

@RefreshScope
@RestController
@Api(tags = "产品服务消费 API")
@RequestMapping("/consumer")
public class ConsumerController {
	
	@Value("${spring.application.name:}")
	private String appName;
	
	@Value("${commonConfig:}")
	private String commonConfigValue;
	
	@Value("${configValue:}")
	private String configValue;
	
	@Autowired
	private ConsumerService consumerService;
	
	@GetMapping
	public String getAppInfo(){
		return appName;
	}
	
	@GetMapping("/name")
	public String index(){
		return "this is service-consumer.";
	}
	
	@ApiOperation(value="获取公共配置")
	@GetMapping("/commonConfigValue")
	public String getCommonConfigValue(){
		return commonConfigValue;
	}
	
	@GetMapping("/configValue")
	public String getConfigValue(){
		return configValue;
	}
	
	@ApiOperation(value="分页获取产品线")
	@GetMapping("/products")
	public Pagination<Product> getProducts(Pagination<Product> page,Product product) throws Exception{
		Pagination<Product> pageData = consumerService.getProducts(page, product);
		return pageData;
	}
	
	@GetMapping("/products/{sku}")
	public Product getProductsBySku(@PathVariable String sku) throws Exception{
		return consumerService.getProductBySku(sku);
	}
	
	@GetMapping("/products/combo/{sku}")
	public List<Combo> getComboListBySku(@PathVariable String sku) throws Exception{
		return consumerService.getComboListBySku(sku); 
	}
}






