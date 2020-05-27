package com.efe.ms.productservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.efe.ms.productservice.domain.ProductLine;

/**
 * 
 * <p>产品线dao: </p> 
 * @author Liu TianLong
 * 2018年8月24日 下午5:55:28
 */
public interface ProductLineRepository extends JpaRepository<ProductLine, Long>{

}
