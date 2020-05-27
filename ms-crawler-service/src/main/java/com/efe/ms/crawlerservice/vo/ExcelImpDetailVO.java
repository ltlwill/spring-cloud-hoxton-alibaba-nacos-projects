package com.efe.ms.crawlerservice.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class ExcelImpDetailVO implements Serializable{
	
	private String transactionDate;
	private String transactionTime;
	private String accountName;
	private String accountNo;
	private String bankName;
	private Double income;
	private Double expend;
	
}
