package com.efe.ms.productservice.service;

import java.util.List;

import com.efe.ms.productservice.domain.ProductLine;

/**
 * 
 * <p>产品线(ProductLine)业务接口: </p> 
 * @author Liu TianLong
 * 2018年8月24日 下午5:54:19
 */
public interface ProductLineService {
	
	List<ProductLine> findAll();

}
