package com.efe.ms.crawlerservice.model.ali1688;

import com.efe.ms.crawlerservice.model.common.SerializationEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DescAttribute extends SerializationEntity {

	private String name;
	private String value;
	private Boolean crossAttr; // 是否是跨境属性（true:跨境属性，false:一般属性）
}
