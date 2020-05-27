package com.efe.ms.crawlerservice.service.common;

import com.efe.ms.crawlerservice.model.common.DataCollectionTask;
import com.efe.ms.crawlerservice.vo.PaginationVO;

/**
 * 数据采集任务业务接口
 * 
 * @author Tianlong Liu
 * @2020年5月8日 下午5:58:02
 */
public interface DataCollectionTaskService {

	/**
	 * 开始处理数据采集任务
	 * @param task
	 * @return
	 */
	DataCollectionTask processTask(DataCollectionTask task);

	/**
	 * 开始异步处理数据采集任务
	 * @param task
	 * @return
	 */
	DataCollectionTask asyncProcessTask(DataCollectionTask task);

	/**
	 * 新增一个任务
	 * @param task
	 * @return
	 */
	DataCollectionTask add(DataCollectionTask task);

	/**
	 * 更新一个任务
	 * @param task
	 * @return
	 */
	DataCollectionTask update(DataCollectionTask task);

	/**
	 * 异步运行任务
	 * @param task
	 */
	void asyncRunTask(DataCollectionTask task);

	/**
	 * 运行任务
	 * @param task
	 * @return
	 */
	DataCollectionTask runTask(DataCollectionTask task);

	/**
	 * 分页获取任务列表数据
	 * @param task
	 * @param page
	 * @return
	 */
	PaginationVO findAll(DataCollectionTask task, PaginationVO page);

}
