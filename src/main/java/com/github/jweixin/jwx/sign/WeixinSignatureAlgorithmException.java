package com.github.jweixin.jwx.sign;

import javax.servlet.ServletException;

public class WeixinSignatureAlgorithmException extends ServletException {

	private static final long serialVersionUID = 1L;
	
	public WeixinSignatureAlgorithmException(String message) {
		super(message);
	}

}
