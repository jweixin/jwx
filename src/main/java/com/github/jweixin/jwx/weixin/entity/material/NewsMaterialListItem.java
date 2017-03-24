package com.github.jweixin.jwx.weixin.entity.material;

/**
 * 永久图文消息素材列表项
 * 
 * @author Administrator
 *
 */
public class NewsMaterialListItem {
	// 素材id
	private String mediaId;
	// 这篇图文消息素材的最后更新时间
	private String updateTime;
	// 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
	private NewsMaterial content;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public NewsMaterial getContent() {
		return content;
	}

	public void setContent(NewsMaterial content) {
		this.content = content;
	}

}
