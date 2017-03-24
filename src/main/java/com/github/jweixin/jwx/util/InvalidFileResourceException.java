package com.github.jweixin.jwx.util;

/**
 * 无效的文件资源异常
 * @author Administrator
 *
 */
public class InvalidFileResourceException extends WeixinInterfaceException {
	
	private static final long serialVersionUID = 1L;

	public InvalidFileResourceException(String msg) {
		super(msg);
	}

	public InvalidFileResourceException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
