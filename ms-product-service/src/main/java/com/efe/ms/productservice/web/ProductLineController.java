package com.efe.ms.productservice.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.productservice.domain.ProductLine;
import com.efe.ms.productservice.service.ProductLineService;

/**
 * 
 * <p>
 * 产品线控制器:
 * </p>
 * 
 * @author Liu TianLong 2018年8月24日 下午5:21:23
 */
@Api(tags="产品线API")
@RefreshScope
@RestController
@RequestMapping("/productline")
public class ProductLineController extends BaseController {

	@Autowired
	private ProductLineService productLineService;
	
	@Value("${customProps.remark:}")
	private String configRemark;
	
//	@Value("${commonConfig.testValue}")
	private String commonConfigValue;

	@ApiOperation(value="获取所有产品线")
	@GetMapping
	public List<ProductLine> getAllProductLine() {
		logger.info("开始查询产品线......");
		return productLineService.findAll();
	}
	
	@GetMapping("/getConfigProp")
	public String getConfigProperties(){
		return configRemark;
	}
	
	@GetMapping("/getCommonConfig")
	public String getCommonConfig(){
		return commonConfigValue;
	}
	
	@GetMapping("/test")
	public String test(){
		return "test11..";
	}

}
