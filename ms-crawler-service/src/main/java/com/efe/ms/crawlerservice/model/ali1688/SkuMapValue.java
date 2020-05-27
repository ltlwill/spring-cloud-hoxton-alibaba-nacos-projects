package com.efe.ms.crawlerservice.model.ali1688;

import java.math.BigDecimal;

import com.efe.ms.crawlerservice.model.common.SerializationEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class SkuMapValue extends SerializationEntity {

	private String specId;
	private BigDecimal price;
	private Integer saleCount;
	private BigDecimal discountPrice;
	private Integer canBookCount;
	private BigDecimal retailPrice;
	private String skuId;
}
