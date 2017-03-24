package com.github.jweixin.jwx.message.response;

public class Music {
	// 音乐标题，不是必须
	private String title;
	// 音乐描述，不是必须
	private String description;
	// 音乐链接，不是必须
	private String musicurl;
	// 高质量音乐链接，WIFI环境优先使用该链接播放音乐，不是必须
	private String hqmusicurl;
	// 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
	private String thumbMediaId;

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

	public String getMusicurl() {
		return musicurl;
	}

	public void setMusicurl(String musicurl) {
		this.musicurl = musicurl;
	}

	public String getHqmusicurl() {
		return hqmusicurl;
	}

	public void setHqmusicurl(String hqmusicurl) {
		this.hqmusicurl = hqmusicurl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

}
