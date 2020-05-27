package com.efe.ms.crawlerservice.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory extends SerializationEntity {
	
	// 类目ID
	private String id;
	// 类目名称
	private String name;

}
