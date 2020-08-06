package com.efe.ms.exampleservice.service;

import com.efe.ms.exampleservice.model.biz.SysUser;
import com.efe.ms.exampleservice.vo.Pagination;

/**
 * 
 * 
 * @author Tianlong Liu
 * 2020年8月4日 下午7:02:13
 */
public interface SysUserService {
	
	Pagination<SysUser> findAllUser(SysUser user,Pagination<SysUser> page);

}
