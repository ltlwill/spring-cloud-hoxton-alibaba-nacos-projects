
package com.efe.ms.crawlerservice.model.ali1688;

import com.efe.ms.crawlerservice.model.common.SerializationEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class SkuPropValue extends SerializationEntity {

	private String name;
	private String imageUrl;
}
