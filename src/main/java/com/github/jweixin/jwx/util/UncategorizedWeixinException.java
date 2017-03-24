package com.github.jweixin.jwx.util;

/**
 * 未分类的微信运行时异常
 * @author Administrator
 *
 */
public class UncategorizedWeixinException extends WeixinInterfaceException {

	private static final long serialVersionUID = 1L;
	
	public UncategorizedWeixinException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public UncategorizedWeixinException(String msg) {
		super(msg);
	}

}
