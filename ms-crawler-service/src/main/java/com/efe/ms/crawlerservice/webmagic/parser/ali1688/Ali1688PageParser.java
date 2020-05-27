package com.efe.ms.crawlerservice.webmagic.parser.ali1688;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efe.ms.crawlerservice.model.ali1688.Ali1688Product;
import com.efe.ms.crawlerservice.model.ali1688.DescAttribute;
import com.efe.ms.crawlerservice.model.ali1688.DescDetail;
import com.efe.ms.crawlerservice.model.ali1688.GalleryImage;
import com.efe.ms.crawlerservice.model.ali1688.SkuDetail;
import com.efe.ms.crawlerservice.model.common.Product;
import com.efe.ms.crawlerservice.model.common.ProductCategory;
import com.efe.ms.crawlerservice.model.common.Seller;
import com.efe.ms.crawlerservice.webmagic.parser.common.BasePageParser;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SimpleHttpClient;
import us.codecraft.webmagic.selector.Html;

/**
 * ali 1688页面解析器
 * 
 * @author Tianlong Liu
 * @2020年4月13日 下午6:16:32
 */
public class Ali1688PageParser extends BasePageParser<Product> {

	private static final String DETAIL_CONFIG_FLAG = "var iDetailConfig =";
	private static final String DETAIL_DATA_FLAG = "var iDetailData =";
	private static final String DETAIL_ALL_TAG_FLAG = "iDetailData.allTagIds =";
	private static final String DETAIL_CATEGORY_RENDER_FLAG = "iDetailData.registerRenderData(";
	private static final String VAR_DESC_FLAG = "var desc='";

	private static final String DETAIL_CONFIG_KEY = "detailConfig";
	private static final String DETAIL_DATA_KEY = "detailData";

	private static final String DEFAULT_CHAR_SET_STR = "GBK";

	@Override
	public Product parse(Page page) throws Exception {
		Html html = page.getHtml();
		Map<String, JSONObject> detailMap = extractJsonInfo(html.toString());
		JSONObject detailConfig = detailMap.get(DETAIL_CONFIG_KEY);
		JSONObject detailData = detailMap.get(DETAIL_DATA_KEY);
		return parseAsProduct(page, detailConfig, detailData);
	}

	@Override
	protected String defaultCharset() {
		return DEFAULT_CHAR_SET_STR;
	}

	/**
	 * 从页面中提取出 JS变量 iDetailConfig、iDetailData 的值
	 * 
	 * @param html
	 * @return
	 */
	private Map<String, JSONObject> extractJsonInfo(String html) {
		Map<String, JSONObject> map = new HashMap<>();
		String dcFlag = DETAIL_CONFIG_FLAG, ddFlag = DETAIL_DATA_FLAG, ddaFlag = DETAIL_ALL_TAG_FLAG;
		int dcIdx = html.indexOf(dcFlag), ddIdx = html.indexOf(ddFlag), ddaIdx = html.indexOf(ddaFlag);
		if (dcIdx < 0 || ddIdx < 0 || ddaIdx < 0 || dcIdx >= ddIdx || ddIdx >= ddaIdx) {
			throw new RuntimeException("解析页面出错，找不到相关的标识信息");
		}
		String dcStr = html.substring(dcIdx + dcFlag.length(), ddIdx).trim();
		dcStr = dcStr.endsWith(";") ? dcStr.substring(0, dcStr.lastIndexOf(";")) : dcStr;
		String ddStr = html.substring(ddIdx + ddFlag.length(), ddaIdx).trim();
		ddStr = ddStr.endsWith(";") ? ddStr.substring(0, ddStr.lastIndexOf(";")) : ddStr;
		map.put(DETAIL_CONFIG_KEY, JSON.parseObject(dcStr));
		map.put(DETAIL_DATA_KEY, JSON.parseObject(ddStr));
		return map;
	}

	/**
	 * 解析为product对象
	 * 
	 * @param page
	 * @param cfg
	 * @param data
	 * @return
	 */
	private Ali1688Product parseAsProduct(Page page, JSONObject cfg, JSONObject data) {
		Html html = page.getHtml();
		// base product info
		Ali1688Product product = parseProductBaseInfo(page,html,cfg);
		// seller info
		product.setSeller(parseSellerInfo(html, cfg));
		// sku info
		product.setSku(parseSkuDetailInfo(html, data));
		// details info
		product.setDetails(parseDetailInfo(html));
		// images info
		product.setGalleryImages(parseGalleryImages(html));
		return product;
	}
	
	/**
	 * 解析产品基础信息
	 * @param page
	 * @param html
	 * @param cfg
	 * @return
	 */
	private Ali1688Product parseProductBaseInfo(Page page,Html html,JSONObject cfg) {
		Ali1688Product product = new Ali1688Product();
		product.setProductId(getString(html.xpath("//meta[@name=\"b2c_auction\"]/@content")));
		product.setProductName(getString(html.xpath("//meta[@property=\"og:title\"]/@content")));
		product.setProductImageUrl(getString(html.xpath("//meta[@property=\"og:image\"]/@content")));
		product.setProductLinkUrl(getString(page.getUrl()));
		product.setKeywords(getString(html.xpath("//meta[@name=\"keywords\"]/@content")));

		product.setCategoryId(getStringByJSONPath(cfg, "$.catid"));
		product.setUnit(getStringByJSONPath(cfg, "$.unit"));
		product.setPriceUnit(getStringByJSONPath(cfg, "$.priceUnit"));
		product.setBeginPriceStr(getStringByJSONPath(cfg, "$.refPrice"));
		product.setBeginPrice(toDoubleAsZeroWhenException(product.getBeginPriceStr()));
		product.setBeginAmount(getStringByJSONPath(cfg, "$.beginAmount"));
		product.setStatus(Product.Status.VALID);
		product.setCreateTime(LocalDateTime.now());
		
		// 解析类目信息
		parseAndSetProductCategoryInfo(page,html,product);
		return product;
	}
	
	/**
	 * 解析产品类目相关信息
	 * @param page
	 * @param html
	 * @param product
	 */
	private void parseAndSetProductCategoryInfo(Page page,Html html,Ali1688Product product) {
		try {
			String htmlStr = html.toString();
			int bIdx = htmlStr.indexOf(DETAIL_CATEGORY_RENDER_FLAG);
			if(bIdx < 0) {
				return;
			}
			htmlStr = htmlStr.substring(bIdx + DETAIL_CATEGORY_RENDER_FLAG.length());
			int eIdx = htmlStr.indexOf("</script>");
			if(eIdx < 0) {
				return;
			}
			String jsonStr = htmlStr.substring(0, eIdx);
			jsonStr = jsonStr == null ? "" : jsonStr.trim();
			jsonStr = jsonStr.endsWith(");") ? jsonStr.substring(0, jsonStr.lastIndexOf(");")) : jsonStr;
			JSONObject json = JSON.parseObject(jsonStr);
			if(json != null && json.containsKey("sellerInf") && json.containsKey("categoryId")) {
				product.setCategoryId(getStringByJSONPath(json, "$.categoryId"));
				product.setCategoryName(getStringByJSONPath(json, "$.categoryName"));
				String stock = getStringByJSONPath(json, "$.stock");
				product.setStock(StringUtils.isBlank(stock) ? null : Integer.valueOf(stock));
				String categoryList = getStringByJSONPath(json, "$.categoryList");
				product.setCategoryList(StringUtils.isBlank(categoryList) ? null : JSON.parseArray(categoryList, ProductCategory.class));
			}
		}catch (Exception e) {
			logger.error(String.format("解析产品类目信息失败,url=%s", product.getProductLinkUrl()));
		}
	}

	/**
	 * 解析卖家信息
	 * 
	 * @param html
	 * @param cfg
	 * @return
	 */
	private Seller parseSellerInfo(Html html, JSONObject cfg) {
		Seller seller = new Seller();
		seller.setSellerId(getStringByJSONPath(cfg, "$.userId"));
		seller.setSellerName(getString(html.xpath("//div[@class=\"ali-masthead-container\"]//div[@class=\"company-name\"]/@title")));
		seller.setLoginId(getStringByJSONPath(cfg, "$.loginId"));
		seller.setMemberId(getStringByJSONPath(cfg, "$.memberid"));
		seller.setCompanySiteLink(getStringByJSONPath(cfg, "$.companySiteLink"));
		seller.setBusinessModel(
				getString(html.xpath("//div[@class*=\"mod-supplierInfoSmall\"]//div[@class=\"detail\"]//span[@class=\"biz-type-model\"]/text()")));
		seller.setLocation(getString(
				html.xpath("//div[@class*=\"mod-supplierInfoSmall\"]//div[@class=\"detail\"]//div[@class*=\"address\"]//span[@class=\"disc\"]/text()")));
		return seller;
	}

	/**
	 * 解析SKU信息
	 * 
	 * @param html
	 * @param skuData
	 * @return
	 */
	private SkuDetail parseSkuDetailInfo(Html html, JSONObject skuData) {
		String skuJsonStr = getStringByJSONPath(skuData, "$.sku");
		SkuDetail detail = null;
		if (StringUtils.isBlank(skuJsonStr)) {
			detail = new SkuDetail();
		}else {
			detail = JSON.parseObject(skuJsonStr, SkuDetail.class);
		}
		String mmonthSaleStr = getString(html.xpath("//div[@class=\"widget-custom-container\"]//p[@class=\"bargain-number\"]//a[@rel=\"nofollow\"]//em[@class=\"value\"]/text()"));
		String reviewCountStr = getString(html.xpath("//div[@class=\"widget-custom-container\"]//p[@class=\"satisfaction-number\"]//a[@rel=\"nofollow\"]//em[@class=\"value\"]/text()"));
		detail.setMonthlySaleCount(toInteger(mmonthSaleStr));
		detail.setReviewCount(toInteger(reviewCountStr));
		return detail;
	}

	/**
	 * 解析明细信息
	 * 
	 * @param html
	 * @return
	 */
	private DescDetail parseDetailInfo(Html html) {
		DescDetail detail = new DescDetail();
		List<DescAttribute> attrs = new ArrayList<DescAttribute>();
		// 跨境属性
		String attrHtml = html.css("div.detail-other-attr-content > div.kuajing-attribues").get();
		Document doc = StringUtils.isBlank(attrHtml) ? null : Jsoup.parse(attrHtml);
		Elements eles = doc == null ? null : doc.select("dd.fd-clr > span");
		if (eles != null && eles.size() > 0) {
			eles.forEach(ele -> attrs.add(new DescAttribute(trimString(ele.select("b").get(0).text()),
					trimString(ele.select("em").get(0).text()), true)));
		}

		// 常规属性
		attrHtml = html.css("div#mod-detail-attributes > div.obj-content").get();
		doc = StringUtils.isBlank(attrHtml) ? null : Jsoup.parse(attrHtml);
		List<String> names = new ArrayList<String>();
		Elements nameEles = doc == null ? null : doc.select("tbody > tr > td.de-feature");
		if (nameEles != null && nameEles.size() > 0) {
			nameEles.forEach(nameEle -> {
				names.add(trimString(nameEle.text()));
			});
		}
		List<String> values = new ArrayList<String>();
		Elements valEles = doc.select("tbody > tr > td.de-value");
		if (valEles.size() > 0) {
			valEles.forEach(valEle -> {
				values.add(trimString(valEle.text()));
			});
		}
		if (names.size() > 0) {
			for (int i = 0; i < names.size(); i++) {
				if (StringUtils.isNotBlank(names.get(i))) {
					attrs.add(new DescAttribute(names.get(i), getFromList(values, i), false));
				}
			}
		}
		detail.setAttributes(attrs);

		// 描述信息
//		detail.setDescription(getString(html.xpath("//div[@id=\"desc-lazyload-container\"]/html()"))); // 描述信息是异步加载的
		String descriptionUrl = getString(html.xpath("//div[@id=\"desc-lazyload-container\"]/@data-tfs-url"));
		detail.setDescriptionUrl(descriptionUrl);
		String resStr = requestDescription(descriptionUrl); // 发送http请求描述信息
		resStr = resStr == null ? "" : resStr.trim();
		if(StringUtils.isNotBlank(resStr)) {
			if(descriptionUrl.contains("desc.alicdn.com")) {
				resStr = resStr.contains(VAR_DESC_FLAG) ? resStr.substring(VAR_DESC_FLAG.length() + 1) : resStr;
				resStr = resStr.endsWith("';") ? resStr.substring(0, resStr.lastIndexOf("';")) : resStr;
				detail.setDescription(resStr);
			}else if(descriptionUrl.contains("img.alicdn.com/tfscom")) {
				resStr = resStr.substring(resStr.indexOf("{"));
				resStr = resStr.endsWith(";") ? resStr.substring(0, resStr.lastIndexOf(";")) : resStr;
				detail.setDescription(getStringByJSONPath(resStr, "$.content")); 
			}
		}
		return detail;
	}

	/**
	 * 解析相册信息
	 * @param html
	 * @return
	 */
	private List<GalleryImage> parseGalleryImages(Html html) {
		String urlHtml = html.css("div.mod-detail-gallery div#dt-tab > .tab-content-container > ul").get();
		if (StringUtils.isBlank(urlHtml)) {
			return null;
		}
		Document doc = Jsoup.parse(urlHtml);
		Elements liEles = doc.select("li.tab-trigger");
		if (liEles == null || liEles.size() == 0) {
			return null;
		}
		List<GalleryImage> images = new ArrayList<GalleryImage>();
		liEles.forEach(ele -> {
			GalleryImage img = new GalleryImage();
			String dataImgsStr = ele.attr("data-imgs");
			JSONObject dataImg = JSON.parseObject(dataImgsStr);
			img.setPreviewUrl(getStringByJSONPath(dataImg, "$.preview"));
			img.setOriginalUrl(getStringByJSONPath(dataImg, "$.original"));
			Elements eles = ele.getElementsByTag("a");
			if (eles.size() > 0) {
				Element ee = eles.get(0);
				img.setDetailUrl(ee.attr("href"));
				eles = ee.getElementsByTag("img");
				if (eles.size() > 0) {
					ee = eles.get(0);
					img.setSmallUrl(ee.attr("src"));
				}
			}
			images.add(img);
		});
		return images;
	}

	private String getFromList(List<String> list, int idx) {
		if (idx >= list.size()) {
			return "";
		}
		return list.get(idx);
	}
	
	private String requestDescription(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		String desc = null;
		try {
//			desc = SimpleRequestUtil.doGetAsString(url, defaultCharset());
			Page page = new SimpleHttpClient().get(url);
			return page == null ? "" : page.getRawText();
		} catch (Exception e) {
			logger.error(String.format("获取描述信息失败,URL=%s", url), e);
		}
		return desc;
	}
}
