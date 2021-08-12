package com.efe.ms.translationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efe.ms.translationservice.dao.SysUserDao;
import com.efe.ms.translationservice.model.biz.SysUser;
import com.efe.ms.translationservice.vo.Pagination;

/**
 * 
 * @author Tianlong Liu
 * 2020年8月4日 下午7:02:36
 */

//@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public Pagination<SysUser> findAllUser(SysUser user,Pagination<SysUser> page) {
		List<SysUser> users = sysUserDao.getListPage(user,page.toRowBounds());
		long total = sysUserDao.count(user);
		return page.with(users,total);
	}
}
