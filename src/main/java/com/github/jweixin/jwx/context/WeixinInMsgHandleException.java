package com.github.jweixin.jwx.context;

import javax.servlet.ServletException;

/**
 * 微信请求消息处理异常
 * @author Administrator
 *
 */
public class WeixinInMsgHandleException extends ServletException {

	private static final long serialVersionUID = 1L;
	
	public WeixinInMsgHandleException(String message) {
		super(message);
	}
	
	public WeixinInMsgHandleException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

}
