package com.efe.ms.crawlerservice.service.common;

import java.util.List;

import com.efe.ms.crawlerservice.model.common.SysUser;
import com.efe.ms.crawlerservice.model.common.User;

public interface UserService {

	List<User> findAll();
	
	List<SysUser> findAllFromMySql();
	
	List<User> findByName(String name);
	
	List<User> findAllUsers(User user);
	
	User addUser(User user);
}
