package com.efe.ms.crawlerservice.model.common;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends SerializationEntity{

	@Id
	private String id;
	private String name;
	private String nickName;
	private String accessToken;
	private String password;
	private String phoneNo;
	private List<Address> address;
	private Integer age;
	private String introduction;
	private String avatar;
	private String keyword;
	private List<String> roles;
	
	public User(String id,String name){
		this.id = id;
		this.name = name;
	}
}
