package com.github.jweixin.jwx.context;

/**
 * 微信参数设置类
 * 
 * @author zzx
 * @since 2017-7-4
 * @version 1.0
 *
 */
public final class WeixinSetting {
	/* 微信上下文url，不包括web应用的上下文部分，不能为空，是识别微信上下文的唯一凭证 */
	private String url;
	/* 微信令牌值，用于微信签名认证，与公众号里面的token值要一致，要不然签名会失败 */
	private String token;
	/* 消息密钥，与公众号要一致 */
	private String encodingAESKey;
	/* 应用ID */
	private String appID;
	/* 应用密钥 */
	private String appSecret;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
