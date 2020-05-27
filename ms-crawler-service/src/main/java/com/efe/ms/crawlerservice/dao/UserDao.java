package com.efe.ms.crawlerservice.dao;

import java.util.List;

import com.efe.ms.crawlerservice.model.common.User;

/**
 * user dao
 *
 * @author TianLong Liu
 * @date 2019年11月6日 下午3:40:03
 */
public interface UserDao extends BaseDao<User>{
	
	List<User> findAll(User user);
	
	List<User> getByIds(List<String> ids);
	
	void deleteByIds(List<String> ids);

}
