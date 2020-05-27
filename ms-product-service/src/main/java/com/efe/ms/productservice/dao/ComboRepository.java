package com.efe.ms.productservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.efe.ms.productservice.domain.Combo;

/**
 * 
 * <p>combo dao: </p> 
 * @author Liu TianLong
 * 2018年10月16日 上午11:55:28
 */
public interface ComboRepository extends JpaRepository<Combo, Long>,JpaSpecificationExecutor<Combo> {
	
	@Query(value="select a from Combo a where concat(a.mainId)=:sku order by a.order asc") // JPQL的SQL语法
	List<Combo> getComboListBySku(@Param("sku") String sku);

}
