package com.efe.ms.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efe.ms.productservice.dao.ProductLineRepository;
import com.efe.ms.productservice.domain.ProductLine;

/**
 * 
 * <p>产品线业务实现类: </p> 
 * @author Liu TianLong
 * 2018年8月24日 下午5:55:05
 */
@Service
public class ProductLineServiceImpl implements ProductLineService {
	
	@Autowired
	private ProductLineRepository productLineRepository;

	@Override
	public List<ProductLine> findAll() {
		return productLineRepository.findAll();
	}

}
