package com.efe.ms.crawlerservice.model.ali1688;

import java.math.BigDecimal;
import java.util.List;

import com.efe.ms.crawlerservice.model.common.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class Ali1688Product extends Product {
	
	// 价格
	protected BigDecimal price;
	// 货币
	protected String currency;
	// 购买单位
	protected String unit;
	// 价格单位
	protected String priceUnit;
	// 起拍数量
	protected String beginAmount;
	// 起拍价格
	protected Double beginPrice;
	// 起拍价格字符串
	protected String beginPriceStr;
	// 关键词
	protected String keywords;
	// sku明细
	protected SkuDetail sku;
	// 明细
	protected DescDetail details;
	
	protected List<GalleryImage> galleryImages;
	
}
