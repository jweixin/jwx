package com.github.jweixin.jwx.context;

import javax.servlet.ServletException;

/**
 * 初始化微信配置异常
 * @author Administrator
 *
 */
public class InitialWeixinConfigureException extends ServletException {

	private static final long serialVersionUID = 1L;
	
	public InitialWeixinConfigureException(String message) {
		super(message);
	}
	
	public InitialWeixinConfigureException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

}
