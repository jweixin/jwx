package com.github.jweixin.jwx.message.response;

/**
 * 图片
 * @author Administrator
 *
 */
public class Image {
	// 通过素材管理中的接口上传多媒体文件，得到的id。
	private String mediaId;
	
	public Image() {
	}

	public Image(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
