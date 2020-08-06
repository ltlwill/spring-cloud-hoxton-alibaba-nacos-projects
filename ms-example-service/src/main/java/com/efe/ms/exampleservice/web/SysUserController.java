package com.efe.ms.exampleservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efe.ms.common.vo.BusinessResult;
import com.efe.ms.exampleservice.model.biz.SysUser;
import com.efe.ms.exampleservice.service.SysUserService;
import com.efe.ms.exampleservice.vo.Pagination;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "系统用户API")
@RequestMapping("/users")
public class SysUserController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@GetMapping
	@ApiOperation(value="获取所有系统用户")
	public BusinessResult<?> findAllUser(SysUser user,Pagination<SysUser> page) {
		return BusinessResult.success(sysUserService.findAllUser(user,page));
	}
	
}
