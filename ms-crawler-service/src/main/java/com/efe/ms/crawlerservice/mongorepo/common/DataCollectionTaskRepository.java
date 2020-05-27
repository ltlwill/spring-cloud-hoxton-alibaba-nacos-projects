package com.efe.ms.crawlerservice.mongorepo.common;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.efe.ms.crawlerservice.model.common.DataCollectionTask;

public interface DataCollectionTaskRepository extends MongoRepository<DataCollectionTask, String> {
	/**
	 *  按关键词查询（ (?i)是java正则表达式忽略大小写的意思）
	 * @param example
	 * @param keyword
	 * @param page
	 * @return
	 */
	@Query(value="{$or: [{id: {$regex: \"^.*(?i)?1.*$\"}},{keywords: {$regex: \"^.*(?i)?1.*$\"}},{\"userName\": {$regex: \"^.*(?i)?1.*$\"}}]}")
	Page<DataCollectionTask> findAllByKeyword(Example<DataCollectionTask> example,String keyword,Pageable page);
}
