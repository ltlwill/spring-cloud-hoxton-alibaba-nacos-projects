package com.efe.ms.crawlerservice.mongorepo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.efe.ms.crawlerservice.model.common.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	List<User> findByName(String name);
}
