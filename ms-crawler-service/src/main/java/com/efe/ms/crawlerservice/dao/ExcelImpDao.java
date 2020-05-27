package com.efe.ms.crawlerservice.dao;

import java.util.List;

import com.efe.ms.crawlerservice.model.common.ExcelImp;

/**
 * excel 导入dao
 *
 * @author TianLong Liu
 * @date 2019年11月6日 下午3:40:03
 */
public interface ExcelImpDao extends BaseDao<ExcelImp>{
	
	void deleteByIds(List<String> ids);
	
	List<ExcelImp> getByIds(List<String> ids);

}
