package com.efe.ms.crawlerservice.webmagic.parser.common;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONPath;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 *  基础页面解析器
 * @author Tianlong Liu
 * @param <T>
 * @2020年4月13日 下午6:07:16
 */
public class BasePageParser<T> implements PageParser<T> {
	
	protected static final Logger logger = LoggerFactory.getLogger(BasePageParser.class);
	
	private static final String DEFAULT_CHAR_SET_STR = "UTF-8";

	@Override
	public T parse(String html) throws Exception{
		return null;
	}

	@Override
	public T parse(Page page) throws Exception{
		return null;
	}

	@Override
	public T parse(Html html) throws Exception{
		return null;
	}
	
	protected String defaultCharset() {
		return DEFAULT_CHAR_SET_STR;
	}
	
	protected String getString(Selectable selectable) {
		if(selectable == null) {
			return "";
		}
		String val = selectable.get();
		return val == null ? "" : val.trim();
	}
	
	protected String getStringByJSONPath(Object obj,String path) {
		Object res = JSONPath.eval(obj,path);
		return res == null ? "" : String.valueOf(res).trim();
	}
	
	protected String trimString(String str) {
		return str == null ? "" : str.trim();
	}
	
	protected BigDecimal toBigDecimal(String str) {
		if(str == null || "".equals(str.trim())) {
			return BigDecimal.valueOf(0);
		}
		return new BigDecimal(str);
	}
	protected BigDecimal toDouble(String str) {
		if(str == null || "".equals(str.trim())) {
			return BigDecimal.valueOf(0);
		}
		return new BigDecimal(str);
	}
	protected Integer toInteger(String str) {
		if(str == null || "".equals(str.trim())) {
			return 0;
		}
		return Integer.valueOf(str);
	}
	
	protected Double toDoubleAsZeroWhenException(String str) {
		if(str == null || "".equals(str.trim())) {
			return Double.valueOf(0);
		}
		try {
			return Double.valueOf(str);
		}catch(Exception e) {
			return Double.valueOf(0);
		}
	}

}
