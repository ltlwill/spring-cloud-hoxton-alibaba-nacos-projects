package com.efe.ms.translationservice.model.biz;

import java.util.List;

import com.efe.ms.common.model.SerializationEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysUser extends SerializationEntity{

	private Long id;
	private String name;
	private String nickName;
	private String accessToken;
	private String password;
	private String phoneNo;
	private Integer age;
	private String introduction;
	private String avatar;
	private String keyword;
	private List<String> roles;
	
	public SysUser(Long id,String name){
		this.id = id;
		this.name = name;
	}
}
