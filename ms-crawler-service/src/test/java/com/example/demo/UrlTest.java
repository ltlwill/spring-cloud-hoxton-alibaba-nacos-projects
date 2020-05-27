package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.efe.ms.crawlerservice.util.SimpleRequestUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SimpleHttpClient;

public class UrlTest {
	
	String url = "https://desc.alicdn.com/i6/610/071/614079536202/TB1hBDJzQL0gK0jSZFA8qwA9pla.desc%7Cvar%5Edesc%3Blang%5Egbk%3Bsign%5E1bf97cb931b2a9ca387f39037040d9a3%3Bt%5E1585276658";

	@Test
	public void test1() {
		SimpleHttpClient c = new SimpleHttpClient();
		Page page = c.get(url);
		System.out.println(page.getRawText());
	}
	
	@Test
	public void test2() throws Exception{
		String str = SimpleRequestUtil.doGetAsString(url, "gbk");
		System.out.println(str); 
	}
	
	@Test
	public void test3() throws Exception{
		String url = "https://detail.1688.com/offer/608561658438.html?spm=a312h.2018_new_sem.dh_002.1.15053da63rLPUJ&tracelog=p4p&clickid=f27b8cc3b0794732ab0c2582dd0d440e&sessionid=14b5d11e4653b28f53e627299b3ceeb4";
		Pattern pattern = Pattern.compile("(.*)detail.1688.com/offer/(\\d+).html(.*)");
		Matcher matcher = pattern.matcher(url);
//		Assert.assertTrue(matcher.find());
		Assert.assertTrue(matcher.matches());
	}
	
	@Test
	public void test4() throws Exception{
		String str = "admin";
		System.out.println(new BCryptPasswordEncoder().encode(str));
	}
}
