package com.efe.ms.translationservice.dao;

import java.util.List;

import com.efe.ms.translationservice.model.biz.SysUser;

/**
 * user dao
 *
 * @author TianLong Liu
 * @date 2019年11月6日 下午3:40:03
 */
public interface SysUserDao extends BaseDao<SysUser> {

	List<SysUser> findAll(SysUser user);

	List<SysUser> getByIds(List<String> ids);

	void deleteByIds(List<String> ids);

}
