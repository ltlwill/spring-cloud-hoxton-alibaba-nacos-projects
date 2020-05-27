package com.efe.ms.crawlerservice.model.ali1688;

import java.util.List;
import java.util.Map;

import com.efe.ms.crawlerservice.model.common.SerializationEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class SkuDetail extends SerializationEntity {

	private String price;
	private String retailPrice;
	private Integer canBookCount;
	private Integer saleCount;
	private Integer monthlySaleCount;
	private Integer reviewCount;
	private String priceRange;
	private List<SkuProp> skuProps;
	private Map<String, SkuMapValue> skuMap;
}
