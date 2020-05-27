package com.efe.ms.crawlerservice.webmagic.parser.common;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

/**
 *  页面解析器接口
 * @author Tianlong Liu
 * @2020年4月13日 下午6:04:31
 */
public interface PageParser<T> {

	T parse(String html) throws Exception;
	
	T parse(Page page) throws Exception;
	
	T parse(Html html) throws Exception;
}
