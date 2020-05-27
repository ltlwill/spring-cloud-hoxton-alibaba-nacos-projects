package com.efe.ms.productservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.efe.ms.productservice.domain.Product;

/**
 * 
 * <p>产品dao </p> 
 * @author Liu TianLong
 * 2018年10月8日 上午11:58:38
 */
public interface ProductRepository extends JpaRepository<Product, Long>,JpaSpecificationExecutor<Product> {
	
	
//	@Query(value="select * from products where concat(id) = ?1",nativeQuery=true) // 原生SQL语法
	@Query(value="select a from Product a where concat(a.id) = ?1") // JPQL的SQL语法,注：需要将id转为字符型(concat(a.id)或cast(a.id as char))，否则mysql出现如a.id='10010796XXXXX'这样也能查询出来
	Product getProductBySku(String sku);

}
