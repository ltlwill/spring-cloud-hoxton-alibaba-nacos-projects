package com.efe.ms.crawlerservice.model.common;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelImp extends BizModel {
	
	private String fileName;
	private String fileLowerName;
	private String fileExt;
	private String absPath;
	private Integer status;
	private String userId;
	private String userName;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date importTime;
	
	public static final class Status{
		public static final int SUCCESS = 1; // 成功
		public static final int FAIL = 0;    // 失败
	}
	
}
