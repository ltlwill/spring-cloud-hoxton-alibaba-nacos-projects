package com.efe.ms.crawlerservice.webmagic.parser.common;

import com.efe.ms.common.util.SpringBeanUtil;

/**
 * 
 * @author Tianlong Liu
 * @2020年4月15日 上午8:58:27
 */
public final class ParserBuilder {

	public static <T> PageParser<T> build(Class<? extends PageParser<T>> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("创建解析器失败", e);
		}

	}

	public static <T> PageParser<T> buildWithSpringBean(Class<? extends PageParser<T>> clazz) {
		try {
			return SpringBeanUtil.getBean(clazz);
		} catch (Exception e) {
			throw new RuntimeException("创建解析器失败", e);
		}

	}
}
