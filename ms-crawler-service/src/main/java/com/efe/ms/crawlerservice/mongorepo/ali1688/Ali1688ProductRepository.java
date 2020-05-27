package com.efe.ms.crawlerservice.mongorepo.ali1688;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;

public interface Ali1688ProductRepository extends MongoRepository<Ali1688Product, String> {

	/**
	 *  按关键词查询（ (?i)是java正则表达式忽略大小写的意思）
	 * @param example
	 * @param keyword
	 * @param page
	 * @return
	 */
	@Query(value="{$or: [{taskNo: {$regex: \"^.*(?i)?1.*$\"}},{productId: {$regex: \"^.*(?i)?1.*$\"}},{productName: {$regex: \"^.*(?i)?1.*$\"}},{\"seller.sellerName\": {$regex: \"^.*(?i)?1.*$\"}}]}")
	Page<Ali1688Product> findAllByKeyword(Example<Ali1688Product> example,String keyword,Pageable page);
}
