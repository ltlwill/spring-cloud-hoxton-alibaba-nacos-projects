package com.efe.ms.crawlerservice.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.efe.ms.crawlerservice.webmagic.processor.github.VuejsGithubProcessor;

@Component
public class CrawlerApplicationRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
//		VuejsGithubProcessor.run();
	}

}
