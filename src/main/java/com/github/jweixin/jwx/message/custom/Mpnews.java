package com.github.jweixin.jwx.message.custom;

/**
 * 图文消息
 * @author Administrator
 *
 */
public class Mpnews {
	/**
	 * 图文消息（点击跳转到图文消息页）的媒体ID
	 */
	private String mediaId;
	
	public Mpnews() {
	}
	
	public Mpnews(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
