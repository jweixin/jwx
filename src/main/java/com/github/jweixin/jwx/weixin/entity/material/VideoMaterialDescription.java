package com.github.jweixin.jwx.weixin.entity.material;

/**
 * 视频素材描述
 * 
 * @author Administrator
 *
 */
public class VideoMaterialDescription {
	// 视频素材的标题
	private String title;
	// 视频素材的描述
	private String introduction;
	// 下载URL
	private String downUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
}
