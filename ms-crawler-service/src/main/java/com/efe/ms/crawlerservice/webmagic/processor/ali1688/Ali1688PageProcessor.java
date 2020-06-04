package com.efe.ms.crawlerservice.webmagic.processor.ali1688;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.efe.ms.crawlerservice.constant.Constants;
import com.efe.ms.crawlerservice.model.common.DataCollectionTask;
import com.efe.ms.crawlerservice.model.common.Product;
import com.efe.ms.crawlerservice.model.common.crawlParams;
import com.efe.ms.crawlerservice.webmagic.parser.ali1688.Ali1688PageParser;
import com.efe.ms.crawlerservice.webmagic.parser.common.ParserBuilder;
import com.efe.ms.crawlerservice.webmagic.pipeline.ali1688.Ali1688ProductsPipeline;
import com.efe.ms.crawlerservice.webmagic.processor.common.BasePageProcessor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * ali 1688页面处理
 * 
 * @author Tianlong Liu
 * @2020年4月10日 上午10:05:14
 */
public class Ali1688PageProcessor extends BasePageProcessor {

	private static final String DEFUALT_CHARSET = "GBK";
//	private static final String ENTRANCE_URL = "https://p4psearch.1688.com/p4p114/p4psearch/offer.htm"; // 抓取数据入口地址
	private static final String KEYWORDS = "修身连衣裙"; // 要抓取的关键词
	private static final int PAGE_COUNT = 10; // 要抓取多少页的数据
	private static final int THREAD_COUNT = 5; // 要开启的线程数
	private static final int PAGE_JSONP_REQUEST_COUNT = 6; // 每一页中的数据需要发送的jsonp 请求的次数
	private static final String KEY_CONCAT_CHAR = "|";

	private crawlParams params;
	private int total = 0;
	private int errorCount = 0;
	private Set<String> keySet = new HashSet<>();
	private DataCollectionTask task;
	

	public Ali1688PageProcessor() {
		this(null, new crawlParams(KEYWORDS, null, PAGE_COUNT, THREAD_COUNT,3,500));
	}

	public Ali1688PageProcessor(DataCollectionTask task) {
		this(task, new crawlParams(KEYWORDS, null, PAGE_COUNT, THREAD_COUNT,3,500));
	}

	public Ali1688PageProcessor(DataCollectionTask task, crawlParams params) {
		this.task = task;
		this.params = params;
	}

	@Override
	public void process(Page page) {
		Selectable url = page.getUrl();
		if (url.regex("(.*)offer.htm(.*)").match()) { // 列表页
//			setTaskNo(page);
			addJsonpTargetUrls(page, params.getPageCount());
		} else if (url.regex("(.*)get_premium_offer_list.json(.*)").match()) { // jsonp 的ajax请求
			JSONObject json = extraDataFromJsonp(page.getRawText());
			addProductDetailTargetUrls(page, json);
		} else if (url.regex("(.*)dj.1688.com/ci_bb(.*)").match()
				|| url.regex("(.*)detail.1688.com/offer/(\\d+).html(.*)").match()) { // 详情页
			try {
				this.total++;
				String key = getProductUniqueKey(page.getHtml());
				if (isProcessed(key)) { // 已经处理过的产品不再处理
					logger.info(String.format("唯一标识：key=[%s] 已处理过", key));
					return;
				}
				Product product = ParserBuilder.build(Ali1688PageParser.class).parse(page); // 解析页面
				product.setTaskNo(task == null ? null : task.getId());
				product.setUserId(task == null ? null : task.getUserId());
				product.setUserName(task == null ? null : task.getUserName());
				product.setSearchKeyword(params.getKeyword());
				page.putField(Constants.PRODUCT_FIELD_NAME, product);
			} catch (Exception e) {
				this.errorCount++;
				logger.error("解析页面异常，url=" + url.toString(), e);
			}
			logger.info("---总：" + total + "；失败：" + errorCount);
		} else {
			logger.debug("未知类型的页面");
		}

	}

	private String getProductUniqueKey(Html html) {
		String productId = getString(html.xpath("//meta[@name=\"b2c_auction\"]/@content"));
		String productName = getString(html.xpath("//meta[@property=\"og:title\"]/@content"));
		return productId + KEY_CONCAT_CHAR + productName; // 产品ID和产品名称组成唯一标识
	}

	private synchronized boolean isProcessed(String key) {
		if (keySet.contains(key)) {
			return true;
		}
		keySet.add(key); // 记录是否处理过此产品，用于过滤重复的产品
		return false;
	}

	private void addProductDetailTargetUrls(Page page, JSONObject json) {
		if (json == null) {
			return;
		}
		Object result = JSONPath.eval(json, "$.data.content.offerResult[*].eurl");
		if (result == null) {
			return;
		}
		List<String> detailUrls = JSON.parseArray(result.toString()).stream().map(itm -> itm.toString())
				.collect(Collectors.toList());
		page.addTargetRequests(detailUrls);
	}

	/**
	 * 将请求列表页的jsonp的ajax请求添加到请求队列
	 * 
	 * @param page
	 * @param pageCount 要请求的前多少页
	 */
	private void addJsonpTargetUrls(Page page, int pageCount) {
		for (int pageNo = 1; pageNo <= pageCount; pageNo++) {
			for (int reqNo = 1; reqNo <= PAGE_JSONP_REQUEST_COUNT; reqNo++) {
				page.addTargetRequest(constructJsonpRequestUrl(pageNo, reqNo));
			}
		}
	}

	/**
	 * 构造请求列表页的jsonp请求URL
	 * 
	 * @param pageNo 第几页
	 * @param reqNo  请求页（每一页的列表数据由6次jsonp请求的数据构成）
	 * @return
	 */
	private String constructJsonpRequestUrl(int pageNo, int reqNo) {
		String urlTpl = "https://data.p4psearch.1688.com/data/ajax/get_premium_offer_list.json?beginpage=${pageNo}&asyncreq=${reqNo}&keywords=${keywords}"
				+ "&sortType=&descendOrder=&province=&city=&priceStart=&priceEnd=&dis=&spm=a2609.11209760.it2i6j8a.36.44292de1DQtbim&cosite=baidujj_pz"
				+ "&trackid=%7Btrackid%7D&location=re&pageid=70026a48k6fyjO&p4pid=6ce81c12ad9c49eda85a7c7c77725e9a&callback=jsonp_${random}_${random2}&_=${random}";
		urlTpl = urlTpl.replaceAll("\\$\\{pageNo\\}", pageNo + "");
		urlTpl = urlTpl.replaceAll("\\$\\{reqNo\\}", reqNo + "");
		urlTpl = urlTpl.replaceAll("\\$\\{keywords\\}", this.params.getKeyword());
		urlTpl = urlTpl.replaceAll("\\$\\{random\\}", generateCallbackRandomNo());
		urlTpl = urlTpl.replaceAll("\\$\\{random2\\}", generateRandomNo());
		return urlTpl;
	}

	private String generateCallbackRandomNo() {
		return (System.nanoTime() + Math.abs(new Random().nextInt(100))) + "";
	}

	private String generateRandomNo() {
		return (Math.abs(new Random().nextInt(10000))) + "";
	}

	@Override
	protected void setHeaders(Site site) {
		super.setHeaders(site);
		site.addHeader("referer",
				"https://p4psearch.1688.com/p4p114/p4psearch/offer.htm?spm=a2609.11209760.it2i6j8a.36.44292de1nHNgKW&cosite=baidujj_pz"
				+ "&keywords=" + this.params.getKeyword() + "&trackid={trackid}&location=re&_rdm=" + System.nanoTime());
	}

	@Override
	protected String defaultCharset() {
		return DEFUALT_CHARSET;
	}
	
	@Override
	protected int getRetryTimes() {
		return this.params.getRetryTimes();
	}
	
	@Override
	protected int getSleepMilliseconds() {
		return this.params.getSleepMilliseconds();
	}

	/*
	 * public static void main(String[] args) throws Exception { new
	 * Ali1688PageProcessor().run(); }
	 */

	public void run() throws Exception {
		buildSpider().run();
	}

	public void runAsync() throws Exception {
		buildSpider().runAsync();
	}

	private Spider buildSpider() throws Exception {
		Spider spider = buildSpider(this).addPipeline(new Ali1688ProductsPipeline())
				.thread(this.params.getThreadCount());
		if (this.params != null) {
			Optional.ofNullable(this.params.getEntranceUrls()).orElse(Collections.emptyList()).stream()
					.forEach(url -> spider.addUrl(url));
		}
		return spider;
	}
}
