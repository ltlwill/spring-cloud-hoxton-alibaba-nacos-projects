package com.efe.ms.crawlerservice.service.ali1688;

import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;
import com.efe.ms.crawlerservice.model.common.crawlParams;
import com.efe.ms.crawlerservice.vo.PaginationVO;

/**
 * ali 1688 产品业务接口
 * @author Tianlong Liu
 * @2020年4月16日 下午3:09:32
 */
public interface Ali1688ProductService {
	
	/**
	 * 开始抓取数据
	 * @param params
	 * @throws Exception
	 */
	void crawlData(crawlParams params) throws Exception;
	
	/**
	 * 保存产品数据
	 * @param product
	 * @return
	 */
	Ali1688Product add(Ali1688Product product);
	
	/**
	 * 保存产品数据（按产品ID去重，如果产品ID已存在，则不再保存）
	 * @param product
	 * @return
	 */
	Ali1688Product addWithDeduplication(Ali1688Product product);
	
	/**
	 * 根据条件分页查询产品数据
	 * @param product
	 * @param page
	 * @return
	 */
	PaginationVO findAll(Ali1688Product product,PaginationVO page);
	
	/**
	 * 根据条件删除产品
	 * @param product
	 */
	void delete(Ali1688Product product);
	
	/**
	 * 删除所有产品
	 */
	void deleteAll();
	

}
