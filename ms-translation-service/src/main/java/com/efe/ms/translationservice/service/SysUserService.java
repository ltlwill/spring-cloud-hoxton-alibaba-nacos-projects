package com.efe.ms.translationservice.service;

import com.efe.ms.translationservice.model.biz.SysUser;
import com.efe.ms.translationservice.vo.Pagination;

/**
 * 
 * 
 * @author Tianlong Liu
 * 2020年8月4日 下午7:02:13
 */
public interface SysUserService {
	
	Pagination<SysUser> findAllUser(SysUser user,Pagination<SysUser> page);

}
