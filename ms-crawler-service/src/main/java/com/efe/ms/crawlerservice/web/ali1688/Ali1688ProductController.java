package com.efe.ms.crawlerservice.web.ali1688;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;
import com.efe.ms.crawlerservice.model.common.crawlParams;
import com.efe.ms.crawlerservice.service.ali1688.Ali1688ProductService;
import com.efe.ms.crawlerservice.vo.BusinessResult;
import com.efe.ms.crawlerservice.vo.PaginationVO;
import com.efe.ms.crawlerservice.web.common.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * ali 1688产品控制器
 * @author Tianlong Liu
 * @2020年4月16日 下午3:19:18
 */
@RestController
@RequestMapping("/ali1688/products")
@Api(tags = "数据采集服务(Ali1688)产品数据API")
public class Ali1688ProductController extends BaseController {
	
	@Autowired
	private Ali1688ProductService productService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "分页获取Ali1688产品数据")
	public BusinessResult findProducts(Ali1688Product product,PaginationVO page) {
		return BusinessResult.success(productService.findAll(product,page));
	}
	
	
	@RequestMapping(value="/crawl",method = RequestMethod.GET)
	public BusinessResult crawlData(crawlParams params) {
		BusinessResult res = BusinessResult.success();
		try {
			productService.crawlData(params);
		}catch (Exception e) {
			logger.error("start crawl ali 1688 product error",e);
			res = BusinessResult.fail();
		}
		return res;
	}
	
	@RequestMapping(value="/delete",method = RequestMethod.GET)
	@ApiOperation(value = "根据条件删除Ali1688产品数据")
	public BusinessResult delete(Ali1688Product product) {
		productService.delete(product);
		return BusinessResult.success();
	}
	
	@RequestMapping("/delete/all")
	public BusinessResult deleteAll() {
		productService.deleteAll();
		return BusinessResult.success();
	}

}
