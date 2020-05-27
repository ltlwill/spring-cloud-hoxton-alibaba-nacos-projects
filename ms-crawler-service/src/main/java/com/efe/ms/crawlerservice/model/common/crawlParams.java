package com.efe.ms.crawlerservice.model.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class crawlParams extends SerializationEntity{

	private String keyword;
	
	private List<String> entranceUrls;
	
	private int pageCount = 20;
	
	private int threadCount = 5;
	
	private int retryTimes = 3;
	
	private int sleepMilliseconds = 500;
}
