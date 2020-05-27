package com.efe.ms.crawlerservice.webmagic.processor.github;

import java.util.Iterator;
import java.util.Map;

import com.efe.ms.crawlerservice.extension.HttpClientDownloader;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * 爬取vuejs作者的github中的项目信息
 * @author Tianlong Liu
 * @2020年3月27日 下午4:25:25
 */
public class VuejsGithubProcessor implements PageProcessor {

	private static Site site = Site.me().setRetryTimes(2).setSleepTime(100);

	/*
	 * public static void main(String[] args) { new VuejsGithubProcessor().run(); }
	 */
	
	public void run() {
		// 使用默认 scheduler
		Spider.create(new VuejsGithubProcessor()).setDownloader(new HttpClientDownloader())
				.addUrl("https://github.com/yyx990803").addPipeline(new MyPipeLine()).thread(3).run();

		// 使用 redis 的scheduler
//		Spider.create(new MyGithubProcessor()).setDownloader(new HttpClientDownloader())
//				.setScheduler(createRedisScheduler()).addUrl("https://github.com/yyx990803").addPipeline(new MyPipeLine())
//				.thread(3).run();
	}

	@Override
	public void process(Page page) {
		/**
		 * if(page.getUrl().regex("https://github.com/yyx990803").match()) { // 主页
		 * page.addTargetRequests(page.getHtml().links().regex("https://github.com/yyx990803/\\w+").all());
		 * }else { // 详情页 page.putField("projectName",
		 * page.getHtml().xpath("/html/body/div[4]/div/main/div[1]/div/div/h1/strong/a/text()").toString());
		 * page.putField("description",
		 * page.getHtml().xpath("/html/body/div[4]/div/main/div[2]/div/details[1]/summary/div[1]/div/span[1]/div/span/text()").toString());
		 * }
		 */
		if (page.getUrl().regex("https://github.com/vuejs/\\w+").match()) { // 非主页
			page.putField("projectName",
					page.getHtml().xpath("/html/body/div[4]/div/main/div[1]/div/div/h1/strong/a/text()").toString());
//					page.getHtml().xpath("/html/body/div[4]/div/main/div[1]/div/div/h1/text()").toString());
			page.putField("description",
//					page.getHtml().$(".repository-content span[itemprop=\"about\"]").xpath("//span/text()").toString());
					page.getHtml().xpath("//span[@itemprop=\"about\"]/text()").toString());
		} else { // 主页
//			page.addTargetRequests(page.getHtml().links().regex("https://github.com/yyx990803/\\w+").all());
//			page.getHtml().css(".pinned-item-list-item .pinned-item-list-item-content .flex-items-center > a").all();
			page.getHtml().$(".pinned-item-list-item .pinned-item-list-item-content .flex-items-center > a", "href")
					.all().forEach(url -> page.addTargetRequest("https://github.com" + url));
		}

	}

	/**
	 * 使用redis scheduler时，每一个URL请求一个后会存入redis中，下载不会在此请求此URL，需要将redis中的记录删掉才能再次请求
	 * 
	 * @return
	 */
	private static Scheduler createRedisScheduler() {
		JedisPoolConfig conf = new JedisPoolConfig();
		conf.setMaxIdle(5);
		conf.setMinIdle(1);
		conf.setTestWhileIdle(true);
		conf.setTestOnBorrow(true);
		JedisPool pool = new JedisPool(conf, "192.168.2.6", 6379, 5000, "redis@1234", 5); // 使用db5
		return new RedisScheduler(pool);
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static class MyPipeLine implements Pipeline {

		public MyPipeLine() {

		}

		@Override
		public void process(ResultItems resultItems, Task task) {
			Map<String, Object> itemsMap = resultItems.getAll();
			Iterator<Map.Entry<String, Object>> iterator = itemsMap.entrySet().iterator();
			Map.Entry<String, Object> entry = null;
			while (iterator.hasNext()) {
				entry = iterator.next();
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
		}

	}

}
