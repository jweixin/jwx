package com.github.jweixin.jwx.weixin.entity.user;

/**
 * 用户标记
 * 
 * @author Administrator
 *
 */
public class UserLabel {
	/**
	 * 用户的标识，对当前公众号唯一
	 */
	private String openid;
	/**
	 * 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
	 */
	private String lang;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
