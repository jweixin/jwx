package com.github.jweixin.jwx.util;

/**
 * 关闭资源失败异常
 * @author Administrator
 *
 */
public class CloseableResourceFailureException extends WeixinInterfaceException {

	private static final long serialVersionUID = 1L;
	
	public CloseableResourceFailureException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public CloseableResourceFailureException(String msg) {
		super(msg);
	}

}
