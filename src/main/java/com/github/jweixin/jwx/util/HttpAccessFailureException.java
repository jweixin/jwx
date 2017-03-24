package com.github.jweixin.jwx.util;

/**
 * http访问失败异常
 * @author Administrator
 *
 */
public class HttpAccessFailureException extends WeixinInterfaceException {

	private static final long serialVersionUID = 1L;
	
	public HttpAccessFailureException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public HttpAccessFailureException(String msg) {
		super(msg);
	}

}
