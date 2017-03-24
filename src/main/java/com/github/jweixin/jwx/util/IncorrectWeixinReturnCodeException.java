package com.github.jweixin.jwx.util;

import com.github.jweixin.jwx.weixin.entity.ReturnCode;

/**
 * 错误的微信返回码异常
 * @author Administrator
 *
 */
public class IncorrectWeixinReturnCodeException extends WeixinInterfaceException {
	
	private static final long serialVersionUID = 1L;
	
	private ReturnCode returnCode;
	
	public IncorrectWeixinReturnCodeException(String msg) {
		super(msg);
	}

	public IncorrectWeixinReturnCodeException(String msg, ReturnCode returnCode) {
		super(msg);
		this.returnCode = returnCode;
	}

	public ReturnCode getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(ReturnCode returnCode) {
		this.returnCode = returnCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
