package com.efe.ms.crawlerservice.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * mongo相关配置
 * @author Tianlong Liu
 * @2020年4月17日 上午9:18:41
 */
@Configuration
public class MongoConfig {

	@Autowired
	private MappingMongoConverter converter;
	
	/**
	 * mongdodb 在使用的key中存在dot(.)符号时出错的问题
	 */
	@PostConstruct
	public void setUpMapKeyDotReplacement() {
		converter.setMapKeyDotReplacement("-\\*dot\\*-");
	}
}
