package com.efe.ms.crawlerservice.dao;

import java.util.List;

import com.efe.ms.crawlerservice.model.common.ExcelImpDetail;

/**
 * excel导入明细dao
 *
 * @author TianLong Liu
 * @date 2019年11月6日 下午3:40:19
 */
public interface ExcelImpDetailDao extends BaseDao<ExcelImpDetail>{
	
	void deleteByIds(List<String> ids);
	
	void deleteByImpIds(List<String> impIds);

}
