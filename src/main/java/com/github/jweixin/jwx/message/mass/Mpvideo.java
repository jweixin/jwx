package com.github.jweixin.jwx.message.mass;

/**
 * 视频
 * @author Administrator
 *
 */
public class Mpvideo {
	/**
	 * 视频素材id
	 */
	private String mediaId;
	/**
	 * 消息的标题
	 */
	private String title;
	/**
	 * 消息的描述
	 */
	private String description;
	
	public Mpvideo() {
	}
	
	public Mpvideo(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
