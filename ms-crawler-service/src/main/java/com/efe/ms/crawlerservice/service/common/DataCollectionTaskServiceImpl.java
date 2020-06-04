package com.efe.ms.crawlerservice.service.common;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.efe.ms.crawlerservice.constant.Constants;
import com.efe.ms.crawlerservice.model.common.DataCollectionTask;
import com.efe.ms.crawlerservice.model.common.crawlParams;
import com.efe.ms.crawlerservice.mongorepo.common.DataCollectionTaskRepository;
import com.efe.ms.crawlerservice.vo.PaginationVO;
import com.efe.ms.crawlerservice.webmagic.processor.ali1688.Ali1688PageProcessor;

/**
 * 数据采集任务业务实现类
 * 
 * @author Tianlong Liu
 * @2020年5月8日 下午5:58:42
 */
@Service
public class DataCollectionTaskServiceImpl extends BaseServiceImpl implements DataCollectionTaskService {

	@Autowired
	private DataCollectionTaskRepository dataCollectionTaskRepository;

	@Override
	public DataCollectionTask add(DataCollectionTask task) {
		Objects.requireNonNull(task);
		task.setCreateTime(LocalDateTime.now());
		task.setStatus(DataCollectionTask.Status.UNPROCESSED);
		return dataCollectionTaskRepository.insert(task);
	}

	@Override
	public DataCollectionTask update(DataCollectionTask task) {
		Objects.requireNonNull(task);
		return dataCollectionTaskRepository.save(task);
	}

	@Override
	public DataCollectionTask processTask(DataCollectionTask task) {
		DataCollectionTask rTask = add(task); // 保存到任务表
		return runTask(rTask);
	}

	@Override
	public DataCollectionTask asyncProcessTask(DataCollectionTask task) {
		DataCollectionTask rTask = add(task); // 保存到任务表
		asyncRunTask(rTask);
		return rTask;
	}

	@Override
	public void asyncRunTask(final DataCollectionTask task) {
		try {
			new Thread(() -> runTask(task)).start();
		} catch (Exception e) {
			logger.error("异步执行DataCollectionTask失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public DataCollectionTask runTask(DataCollectionTask task) {
		if (task == null || StringUtils.isBlank(task.getId())) {
			return task;
		}
		task.setStatus(DataCollectionTask.Status.PROCCCESSING);
		task = update(task);
		try {
			doRunTask(task);
			task.setStatus(DataCollectionTask.Status.PROCESS_SUCCESS);
		} catch (Exception e) {
			task.setStatus(DataCollectionTask.Status.PROCESS_FAIL);
			task.setDebugMessage(e.getMessage());
		} finally {
			task.setEndTime(LocalDateTime.now());
			task = update(task);
		}
		return task;
	}

	private void doRunTask(final DataCollectionTask task) throws Exception {
		crawlParams params = new crawlParams();
		params.setPageCount(task.getPageCount());
		params.setThreadCount(task.getThreadCount());
		if (Integer.valueOf(DataCollectionTask.Type.SINGLE_PRODUCT).equals(task.getType())) { // 按单品采集
			params.setEntranceUrls(task.getUrls());
			new Ali1688PageProcessor(task, params).run();
		} else if (Integer.valueOf(DataCollectionTask.Type.KEYWORD).equals(task.getType())) { // 按关键词采集
			params.setEntranceUrls(Arrays.asList(Constants.Entrance.ALI_RE_1688));
			task.getKeywords().forEach(keyword -> {
				params.setKeyword(keyword);
				try {
					new Ali1688PageProcessor(task, params).run();
				} catch (Exception e) {
					logger.error(String.format("执行数据采集任务出错，task ID=%s", task.getId()), e);
					throw new RuntimeException(e);
				}
			});
		}
	}

	@Override
	public PaginationVO findAll(DataCollectionTask task, PaginationVO page) {
		Example<DataCollectionTask> example = Example.of(task, ExampleMatcher.matching());
		if (StringUtils.isNotBlank(page.getKeyword())) {
			return PaginationVO.from(
					dataCollectionTaskRepository.findAllByKeyword(example, page.getKeyword(), page.toPageRequest()));
		}
		return PaginationVO.from(dataCollectionTaskRepository.findAll(example, page.toPageRequest()));
	}

}
