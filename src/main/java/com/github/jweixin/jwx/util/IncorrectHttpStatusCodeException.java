package com.github.jweixin.jwx.util;

/**
 * http响应状态异常
 * @author Administrator
 *
 */
public class IncorrectHttpStatusCodeException extends WeixinInterfaceException {

	private static final long serialVersionUID = 1L;
	/**
	 * http响应状态
	 */
	private HttpReturnStatus httpStatus;
	
	public IncorrectHttpStatusCodeException(String msg, HttpReturnStatus httpStatus) {
		super(msg);
		this.httpStatus = httpStatus;
	}
	
	public IncorrectHttpStatusCodeException(String msg) {
		super(msg);
	}

	public HttpReturnStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpReturnStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
