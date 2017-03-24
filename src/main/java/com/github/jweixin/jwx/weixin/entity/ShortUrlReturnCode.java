package com.github.jweixin.jwx.weixin.entity;

/**
 * 长链接转短链接返回码
 * @author Administrator
 *
 */
public class ShortUrlReturnCode extends ReturnCode {
	/**
	 * 短链接
	 */
	private String shortUrl;

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

}
