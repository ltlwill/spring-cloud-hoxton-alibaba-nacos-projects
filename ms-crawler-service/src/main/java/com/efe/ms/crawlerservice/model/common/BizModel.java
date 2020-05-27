package com.efe.ms.crawlerservice.model.common;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * <p>基础实体类: </p> 
 * @author Liu TianLong
 * 2019年5月7日 下午5:56:11
 */
@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class BizModel extends SerializationEntity{
	
	@Id
	protected String id;

}
