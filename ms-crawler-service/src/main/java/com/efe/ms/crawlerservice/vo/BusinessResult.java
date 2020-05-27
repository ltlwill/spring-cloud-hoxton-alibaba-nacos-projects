package com.efe.ms.crawlerservice.vo;

import java.util.HashMap;
import java.util.Map;

import com.efe.ms.crawlerservice.model.common.BizModel;


/**
 * 
 * <p>业务处理结果: </p> 
 * @author Liu TianLong
 * 2019年1月10日 下午3:30:49
 */
@SuppressWarnings("serial")
public class BusinessResult extends BizModel {
	private int code;
	private Object data;
	private String message;
	private Map<String, Object> messages = new HashMap<String, Object>();
	private Map<String, Object> datas = new HashMap<String, Object>();
	
	public static final class ResultCode {
		public final static int SUCCESS = 20000; // 成功
		public final static int FAIL = 500;    // 失败
	}

	public BusinessResult() {
	}

	public BusinessResult(int code) {
		this.code = code;
	}
	public BusinessResult(int resultCode,Object data) {
		this.code = resultCode;
		this.data = data;
	}

	public BusinessResult(int resultCode, String message) {
		this.message = message;
	}

	public BusinessResult(int code, Map<String, Object> messages) {
		this.code = code;
		this.messages = messages;
	}

	public BusinessResult addMessage(String key, Object msg) {
		this.messages.put(key, msg);
		return this;
	}
	public BusinessResult addData(String key, Object data) {
		this.datas.put(key, data);
		return this;
	}
	public BusinessResult data(Object data) {
		this.data = data;
		return this;
	}
	public BusinessResult message(String message) {
		this.message = message;
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int resultCode) {
		this.code = resultCode;
	}

	public Map<String, Object> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, Object> messages) {
		this.messages = messages;
	}

	public boolean isSuccess() {
		return this.code == ResultCode.SUCCESS;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public static BusinessResult success(){
		return new BusinessResult(BusinessResult.ResultCode.SUCCESS);
	}
	public static BusinessResult success(Object data){
		return new BusinessResult(BusinessResult.ResultCode.SUCCESS,data);
	}
	public static BusinessResult success(String message){
		return new BusinessResult(BusinessResult.ResultCode.SUCCESS,message);
	}
	
	public static BusinessResult fail(){
		return new BusinessResult(BusinessResult.ResultCode.FAIL);
	}
	public static BusinessResult fail(String message){
		return new BusinessResult(BusinessResult.ResultCode.FAIL,message);
	}
}
