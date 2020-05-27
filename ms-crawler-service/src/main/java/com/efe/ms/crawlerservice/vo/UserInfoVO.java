package com.efe.ms.crawlerservice.vo;

import com.efe.ms.crawlerservice.model.common.BizModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO extends BizModel{
	
	private String name;
	
	private String realName;

}
