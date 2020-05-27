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
public class Address extends SerializationEntity {
	
	private String street;
	
	private Integer no;

}
