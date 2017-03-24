package com.github.jweixin.jwx.context;

import com.github.jweixin.jwx.util.WeixinInterfaceException;

/**
 * 微信接口无法获取access_token异常
 * @author Administrator
 *
 */
public class NoAccessTokenException extends WeixinInterfaceException {
	
	private static final long serialVersionUID = 1L;
	
	public NoAccessTokenException(String msg) {
		super(msg);
	}
	
	public NoAccessTokenException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
