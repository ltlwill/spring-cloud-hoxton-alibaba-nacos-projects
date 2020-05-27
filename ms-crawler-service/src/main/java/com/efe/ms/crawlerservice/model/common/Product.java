package com.efe.ms.crawlerservice.model.common;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *     产品
 * @author Tianlong Liu
 * @2020年4月15日 上午9:47:48
 */
@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BizModel {
	
	// 任务编号
	private String taskNo;
	// 搜索产品的关键词
	private String searchKeyword;
	// 产品ID
	protected String productId; 
	// 产品名称
	protected String productName;
	// 产品图片地址
	protected String productImageUrl;
	// 产品链接地址
	protected String productLinkUrl;
	// 产品类目ID
	protected String categoryId;
	// 产品类目名称
	protected String categoryName;
	// 类目集合
	protected List<ProductCategory> categoryList;
	// 库存
	protected Integer stock;
	// 创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	protected LocalDateTime createTime;
	// 状态
	protected Integer status;
	
	protected Seller seller;
	
	public static final class Status{
		public static final int INVALID = 0; // 无效
		public static final int VALID = 1;   // 有效
	}

}
