package com.efe.ms.common.exception;

/**
 * 自定义额业务异常
 * @author Tianlong Liu
 * @2020年8月11日 下午1:55:18
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -2865833379232904664L;

	// 错误码
	private int errorCode = 500;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public BusinessException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(Throwable cause, int errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message, Throwable cause, int errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
