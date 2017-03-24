package com.github.jweixin.jwx.context;

import javax.servlet.ServletException;

/**
 * 子线程调用微信方法异常
 * @author Administrator
 *
 */
public class InvocationWeixinMethodException extends ServletException {

	private static final long serialVersionUID = 1L;
	
	public InvocationWeixinMethodException(String message) {
		super(message);
	}
	
	public InvocationWeixinMethodException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

}
