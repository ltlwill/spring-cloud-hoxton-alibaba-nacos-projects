package com.efe.ms.crawlerservice.model.common;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 数据采集任务
 * @author Tianlong Liu
 * @2020年5月8日 下午5:14:36
 */
@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataCollectionTask extends BizModel {

	// 任务类型（单品采集、关键词采集）
	private Integer type;
	// 平台(如：1688、淘宝、amazon 等)
	private String platform;
	// 单品采集时的URL集合
	private List<String> urls;
	// 关键词集合
	private List<String> keywords;
	// 处理状态
	private Integer status;
	// 信息
	private String message;
	// 调试信息
	private String debugMessage;
	// 创建人ID
	private String userId;
	// 创建人名称
	private String userName;
	// 前多少页
	private Integer pageCount = 20; // 按关键词, 分类采集时 有效
	// 开启线程数
	private Integer threadCount = 5; // 按关键词, 分类采集时 有效（每个关键词都开启此数据的线程）
	// 创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	private LocalDateTime createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
	private LocalDateTime endTime;
	
	public static final class Type{
		public static final int SINGLE_PRODUCT = 0; // 单品采集
		public static final int KEYWORD = 1; // 关键词采集
	}
	
	public static final class Status{
		public static final int UNPROCESSED = 0;     // 未处理
		public static final int PROCCCESSING = 1;    // 处理中
		public static final int PROCESS_SUCCESS = 2; // 处理结束
		public static final int PROCESS_FAIL = 3;    // 处理失败
	}
	
}
