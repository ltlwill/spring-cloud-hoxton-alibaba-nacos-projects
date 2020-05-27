package com.efe.ms.crawlerservice.model.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelImpDetail extends BizModel {
	
	private Long impId;
	private String accountName;
	private String accountNo;
	private String bankName;
	private String bankNo;
	private String transactionDate;
	private String transactionTime;
	private Double income;
	private Double expend;
	
	private String userId;
	private String userName;
	private Date importTime;
	
	private String fileName;
	private String fileLowerName;
	private Double balance;
	private String toAccountName;
	private String toAccountNo;
	private String toBankName;
	private String summary;
	private String ip;
	
	/**
	 * 标题与字段的对应关系
	 *
	 * @author TianLong Liu
	 * @date 2019年11月19日 下午3:22:54
	 */
	public static class TitleFieldNameMap{
		
		private static final Map<String,String> map = new HashMap<String, String>();
		
		static{
			map.put("交易日期", "transactionDate");
			map.put("交易时间", "transactionTime");
			map.put("户名", "accountName");
			map.put("账号", "accountNo");
			map.put("开户行", "bankName");
			map.put("收入", "income");
			map.put("支出", "expend");
			map.put("余额", "balance");
			map.put("对方户名", "toAccountName");
			map.put("对方账号", "toAccountNo");
			map.put("对方银行", "toBankName");
			map.put("摘要", "summary");
			map.put("IP", "ip");
		}
		
		public static String getFieldName(String title){
			return map.get(title);
		}
		
		public static Map<String,String> TitleFieldMap(){
			return map;
		}
		
	}
	
}
