package com.github.jweixin.jwx.util;

import org.springframework.core.NestedRuntimeException;

/**
 * 微信接口运行时抽象异常类
 * @author Administrator
 *
 */
public abstract class WeixinInterfaceException extends NestedRuntimeException {

	private static final long serialVersionUID = 1L;

	public WeixinInterfaceException(String msg) {
		super(msg);
	}
	
	public WeixinInterfaceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
