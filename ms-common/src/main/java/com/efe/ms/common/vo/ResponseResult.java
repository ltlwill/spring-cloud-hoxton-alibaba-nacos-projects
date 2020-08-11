package com.efe.ms.common.vo;

import com.efe.ms.common.domain.SerializationEntity;

/**
 * 统一响应结果
 * @author Tianlong Liu
 * @2020年8月11日 上午11:05:07
 * @param <T>
 */

@SuppressWarnings("serial")
public class ResponseResult<T> extends SerializationEntity {
	private int code;
	private T result;
	private String message;

	public static final class ResultCode {
		public final static int SUCCESS = 0; // 成功
		public final static int FAIL = 1; // 失败
		public final static int INVALID_TOKEN = 20001; // 无效token
	}

	public ResponseResult() {
	}

	public ResponseResult(int code) {
		this.code = code;
	}

	public ResponseResult(int code, T result) {
		this.code = code;
		this.result = result;
	}

	public ResponseResult<T> data(T result) {
		this.result = result;
		return this;
	}

	public ResponseResult<T> message(String message) {
		this.message = message;
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public static <T> ResponseResult<T> success() {
		return new ResponseResult<T>(ResponseResult.ResultCode.SUCCESS);
	}

	public static <T> ResponseResult<T> success(T result) {
		return new ResponseResult<T>(ResponseResult.ResultCode.SUCCESS).data(result);
	}

	public static <T> ResponseResult<T> fail() {
		return new ResponseResult<T>(ResponseResult.ResultCode.FAIL);
	}

	public static <T> ResponseResult<T> fail(String message) {
		return new ResponseResult<T>(ResponseResult.ResultCode.FAIL).message(message);
	}

	public static <T> ResponseResult<T> fail(int code, String message) {
		return new ResponseResult<T>(code).message(message);
	}
}
