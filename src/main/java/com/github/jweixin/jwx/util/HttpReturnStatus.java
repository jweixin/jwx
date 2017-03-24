package com.github.jweixin.jwx.util;

/**
 * http调用返回状态码
 * 
 * @author Administrator
 *
 */
public class HttpReturnStatus {
	/**
	 * http返回码
	 */
	private int statusCode;
	/**
	 * 原因
	 */
	private String reasonPhrase;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}
}
