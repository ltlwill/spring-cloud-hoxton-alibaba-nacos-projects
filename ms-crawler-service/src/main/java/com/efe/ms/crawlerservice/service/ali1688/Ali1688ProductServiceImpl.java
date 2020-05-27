package com.efe.ms.crawlerservice.service.ali1688;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.efe.ms.crawlerservice.constant.Constants;
import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;
import com.efe.ms.crawlerservice.model.common.DataCollectionTask;
import com.efe.ms.crawlerservice.model.common.crawlParams;
import com.efe.ms.crawlerservice.mongorepo.ali1688.Ali1688ProductRepository;
import com.efe.ms.crawlerservice.service.common.BaseServiceImpl;
import com.efe.ms.crawlerservice.service.common.DataCollectionTaskService;
import com.efe.ms.crawlerservice.vo.PaginationVO;
import com.efe.ms.crawlerservice.webmagic.processor.ali1688.Ali1688PageProcessor;

/**
 * ali 1688产品业务实现类
 * 
 * @author Tianlong Liu
 * @2020年4月16日 下午3:10:42
 */
@Service
public class Ali1688ProductServiceImpl extends BaseServiceImpl implements Ali1688ProductService {
	
	@Autowired
	private Ali1688ProductRepository productRepository;
	@Autowired
	private DataCollectionTaskService dataCollectionTaskService;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Ali1688Product add(Ali1688Product product) {
		if (product == null) {
			throw new RuntimeException("无效参数");
		}
		return productRepository.save(product);
	}

	@Override
	public Ali1688Product addWithDeduplication(Ali1688Product product) {
		Objects.requireNonNull(product, "无效参数");
		Ali1688Product aliProduct = new Ali1688Product();
		aliProduct.setProductId(product.getProductId());
		long count = productRepository.count(Example.of(aliProduct));
		if (count == 0) {
			add(product);
		}
		return product;
	}

	@Override
	public void crawlData(crawlParams params) throws Exception {
		DataCollectionTask task = new DataCollectionTask();
		task.setKeywords(Arrays.asList(params.getKeyword()));
		task.setType(DataCollectionTask.Type.KEYWORD);
		task.setStatus(DataCollectionTask.Status.PROCCCESSING);
		task = dataCollectionTaskService.add(task);
		try {
			params.setEntranceUrls(Arrays.asList(Constants.Entrance.ALI_RE_1688));
			new Ali1688PageProcessor(task.getId(),params).run();
			task.setStatus(DataCollectionTask.Status.PROCESS_SUCCESS);
		}catch (Exception e) {
			task.setStatus(DataCollectionTask.Status.PROCESS_FAIL);
			logger.error("采集数据任务实行失败",e);
			throw e;
		}finally {
			task.setEndTime(LocalDateTime.now());
			dataCollectionTaskService.update(task);
		}
	}

	@Override
	public PaginationVO findAll(Ali1688Product product, PaginationVO page) {
		return findAllByMongoRepo(product,page);
//		return findAllByMongoTemplate(product,page);
	}
	
	private PaginationVO findAllByMongoRepo(Ali1688Product product, PaginationVO page) { 
		ExampleMatcher matcher = ExampleMatcher.matching();
		matcher.withMatcher("productName", GenericPropertyMatchers.contains().ignoreCase());
		Example<Ali1688Product> example = Example.of(product,matcher);
//		PageRequest pageRequest = PageRequest.of(page.getPageNumber(), page.getPageSize(),
//				Sort.by(Direction.DESC, "createTime"));
//				page.getSort());
		if(StringUtils.isBlank(page.getKeyword())) {
			return PaginationVO.from(productRepository.findAll(example, page.toPageRequest()));
		}
		return PaginationVO.from(productRepository.findAllByKeyword(example, page.getKeyword(),page.toPageRequest()));
	}
	
	@SuppressWarnings("unused")
	private PaginationVO findAllByMongoTemplate(Ali1688Product product, PaginationVO page) {
		String regex = "^.*" + page.getKeyword() + ".*$";
		PageRequest pr = page.toPageRequest();
		Example<Ali1688Product> example = Example.of(product);
		Query query = Query.query(Criteria.byExample(example));
		query.addCriteria(Criteria.where("productName").regex(regex).orOperator(Criteria.where("seller.sellerName").regex(regex)));
		query.with(pr);
		query.with(pr.getSort());
		List<Ali1688Product> rows = mongoTemplate.findAllAndRemove(query, Ali1688Product.class);
		page.setRows(rows);
		return page;
	}

	@Override
	public void delete(Ali1688Product product) {
		if (product == null) {
			throw new RuntimeException("无效参数");
		}
		productRepository.delete(product);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

}
