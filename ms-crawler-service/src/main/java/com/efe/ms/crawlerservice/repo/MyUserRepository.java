package com.efe.ms.crawlerservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.efe.ms.crawlerservice.model.common.SysUser;

public interface MyUserRepository extends JpaRepository<SysUser, String>,JpaSpecificationExecutor<SysUser>{

}
