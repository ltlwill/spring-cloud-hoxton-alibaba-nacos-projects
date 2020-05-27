package com.efe.ms.crawlerservice.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.crawlerservice.model.common.DataCollectionTask;
import com.efe.ms.crawlerservice.service.common.DataCollectionTaskService;
import com.efe.ms.crawlerservice.vo.BusinessResult;
import com.efe.ms.crawlerservice.vo.PaginationVO;

/**
 *  数据采集任务控制器
 * @author Tianlong Liu
 * @2020年5月8日 下午5:49:39
 */
@RestController
@RequestMapping("/data/collection")
public class DataCollectionController extends BaseController {

	@Autowired
	private DataCollectionTaskService dataCollectionTaskService;
	
	@RequestMapping(value="/task",method = RequestMethod.POST)
	public BusinessResult addTask(@RequestBody DataCollectionTask task) {
		DataCollectionTask rTask = dataCollectionTaskService.asyncProcessTask(task);
		return BusinessResult.success(rTask);
	}
	
	@RequestMapping("/list")
	public BusinessResult addTask(DataCollectionTask task,PaginationVO page) {
		task.setPageCount(null);
		task.setThreadCount(null);
		return BusinessResult.success(dataCollectionTaskService.findAll(task, page));
	}
}
