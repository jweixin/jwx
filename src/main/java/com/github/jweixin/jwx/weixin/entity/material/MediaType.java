package com.github.jweixin.jwx.weixin.entity.material;

/**
 * 素材类型枚举
 * @author Administrator
 *
 */
public enum MediaType {
	// 图片（image）: 2M，支持PNG\JPEG\JPG\GIF格式
	IMAGE("image"), 
	// 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
	VOICE("voice"), 
	// 视频（video）：10MB，支持MP4格式
	VIDEO("video"), 
	// 缩略图（thumb）：64KB，支持JPG格式
	THUMB("thumb"),
	// 图文消息
	NEWS("news");
	
	private String type;
	
	private MediaType(String type){
		this.type = type;
	}
	
	public String type(){
		return type;
	}

}
