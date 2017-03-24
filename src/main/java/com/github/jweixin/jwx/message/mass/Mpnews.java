package com.github.jweixin.jwx.message.mass;

/**
 * 用于设定即将发送的图文消息
 * @author Administrator
 *
 */
public class Mpnews {
	/**
	 * 用于群发的消息的media_id
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
