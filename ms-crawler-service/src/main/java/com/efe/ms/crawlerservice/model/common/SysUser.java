package com.efe.ms.crawlerservice.model.common;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user")
public class SysUser extends SerializationEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Column(name = "nick_name")
	private String nickName;
	@Column(name = "access_token")
	private String accessToken;
	private String password;
	@Column(name = "phone_no")
	private String phoneNo;
	@Transient
	private List<Address> address;
	private Integer age;
	private String introduction;
	private String avatar;
	private String keyword;
	@Transient
	private List<String> roles;
	
	public SysUser(Long id,String name){
		this.id = id;
		this.name = name;
	}
}
