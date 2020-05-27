package com.efe.ms.crawlerservice.webmagic.processor.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.efe.ms.common.util.SpringBeanUtil;
import com.efe.ms.crawlerservice.config.ApplicationPropsConfig;
import com.efe.ms.crawlerservice.extension.HttpClientDownloader;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.ProxyProvider;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 基础页面处理器
 * 
 * @author Tianlong Liu
 * @2020年4月10日 上午9:53:44
 */
public abstract class BasePageProcessor implements PageProcessor {

	protected static final Logger logger = LoggerFactory.getLogger(BasePageProcessor.class);

	private static final String DEFAULT_CHAR_SET_STR = "UTF-8";

	@Override
	public abstract void process(Page page);

	@Override
	public Site getSite() {
		return createSite();
	}

	protected Site createSite() {
		Site site = Site.me().setRetryTimes(getRetryTimes()).setSleepTime(getSleepMilliseconds());
		site.setCharset(defaultCharset());
		setHeaders(site);
		return site;
	}

	protected int getRetryTimes() {
		return 3;
	}

	protected int getSleepMilliseconds() {
		return 500;
	}

	protected void setHeaders(Site site) {
		site.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
	}

	protected String defaultCharset() {
		return DEFAULT_CHAR_SET_STR;
	}

	protected Downloader createDownloader() {
		HttpClientDownloader downloader = new HttpClientDownloader();
		ProxyProvider provider = createProxyProvider();
		if (provider != null) {
			downloader.setProxyProvider(provider);
		}
		return downloader;
	}

	protected ProxyProvider createProxyProvider() {
		try {
			ApplicationPropsConfig cfg = SpringBeanUtil.getBean(ApplicationPropsConfig.class);
			if (cfg != null && cfg.isEnableProxyPool() && cfg.getProxyPools() != null
					&& cfg.getProxyPools().size() > 0) {
				List<Proxy> proxies = new ArrayList<Proxy>();
				cfg.getProxyPools().forEach(map -> {
					proxies.add(new Proxy(map.get("host"), Integer.valueOf(map.get("port")), map.get("username"),
							map.get("password")));
				});
				return new SimpleProxyProvider(proxies);
			}
		} catch (Exception e) {
			logger.error("获取代理配置信息失败", e);
		}
		return null;
	}

	protected JSONObject extraDataFromJsonp(String jsonp) {
		if (StringUtils.isBlank(jsonp)) {
			return new JSONObject();
		}
		int firstIdx = jsonp.indexOf("("), lastIndx = jsonp.indexOf(")");
		if (firstIdx < 0 || lastIndx < -1) {
			return new JSONObject();
		}
		String str = jsonp.substring(firstIdx + 1, lastIndx);
		return StringUtils.isBlank(str) ? new JSONObject() : JSON.parseObject(str);
	}

	protected String getStringValue(Selectable selectable) {
		if (selectable == null) {
			return "";
		}
		String val = selectable.toString();
		return StringUtils.isBlank(val) ? "" : val.trim();
	}

	public Spider buildSpider(PageProcessor processor) {
		return Spider.create(processor).setDownloader(createDownloader());
	}

	public Spider buildSpider(Class<? extends PageProcessor> clazz) throws Exception {
		return Spider.create(clazz.newInstance()).setDownloader(createDownloader());
	}

	protected String getString(Selectable selectable) {
		if (selectable == null) {
			return "";
		}
		String val = selectable.get();
		return val == null ? "" : val.trim();
	}

	protected String getStringByJSONPath(Object obj, String path) {
		Object res = JSONPath.eval(obj, path);
		return res == null ? "" : String.valueOf(res).trim();
	}

	protected String trimString(String str) {
		return str == null ? "" : str.trim();
	}

}
