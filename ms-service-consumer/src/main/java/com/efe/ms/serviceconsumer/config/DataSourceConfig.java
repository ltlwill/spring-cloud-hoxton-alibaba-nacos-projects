package com.efe.ms.serviceconsumer.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author TianLong Liu
 * @date 2019年8月9日 下午4:48:51
 */
@Configuration
public class DataSourceConfig {

	/**
	 * 注：因此服务不需要数据源，导致tx-lcn分布式事务启动时失败，在此构造一个假的数据源，应用才能正常启动，
	 * 如果此服务又数据源，则就不需要了
	 * @return
	 */
	@Bean
	public DataSource hikariDataSource(){
		return new DummyDataSource();
	}
}
