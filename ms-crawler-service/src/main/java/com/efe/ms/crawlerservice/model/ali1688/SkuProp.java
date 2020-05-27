package com.efe.ms.crawlerservice.model.ali1688;

import java.util.List;

import com.efe.ms.crawlerservice.model.common.SerializationEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class SkuProp extends SerializationEntity {

	private String prop;
	private List<SkuPropValue> value;
}
