package com.efe.ms.crawlerservice.model.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *      卖家
 * @author Tianlong Liu
 * @2020年4月15日 上午9:47:21
 */
@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class Seller extends SerializationEntity {

	protected String loginId;
	// 卖家ID
	protected String sellerId;
	// 卖家名称
	protected String sellerName;
	// 会员ID
	protected String memberId;
	// 商家链接地址
	protected String companySiteLink;
	// 经营模式
	protected String businessModel;
	// 所在位置
	protected String location;
}
