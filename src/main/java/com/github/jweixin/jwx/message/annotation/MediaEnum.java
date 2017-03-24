package com.github.jweixin.jwx.message.annotation;

/**
 * 媒体类型枚举
 * @author Administrator
 *
 */
public enum MediaEnum {
	IMAGE("image"),
	VOICE("voice"),
	VOICE_RECOGNITION("voicerecognition"),
	VIDEO("video"),
	SHORT_VIDEO("shortvideo");
	
	
	private String type;
	
	private MediaEnum(String type){
		this.type = type;
	}
	
	public String type(){
		return this.type;
	}
}
