package com.github.jweixin.jwx.weixin.entity;

/**
 * access_token是公众号的全局唯一接口调用凭据
 * 
 * @author Administrator
 *         access_token的存储至少要保留512个字符空间。access_token的有效期目前为2个小时，需定时刷新，
 *         重复获取将导致上次获取的access_token失效
 */
public class AccessToken {
	private String accessToken;
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}
