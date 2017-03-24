package com.github.jweixin.jwx.message.response;

/**
 * 语音
 * @author Administrator
 *
 */
public class Voice {
	// 通通过素材管理中的接口上传多媒体文件，得到的id
	private String mediaId;
	
	public Voice() {
	}

	public Voice(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
}
