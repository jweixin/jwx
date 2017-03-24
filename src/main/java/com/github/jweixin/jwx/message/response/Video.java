package com.github.jweixin.jwx.message.response;

/**
 * 视频
 * 
 * @author Administrator
 *
 */
public class Video {
	// 通过素材管理中的接口上传多媒体文件，得到的id
	private String mediaId;
	// 缩略图的媒体ID
	private String thumbMediaId;
	// 视频消息的标题，不是必须
	private String title;
	// 视频消息的描述，不是必须
	private String description;

	public Video() {
		super();
	}

	public Video(String mediaId) {
		super();
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
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
