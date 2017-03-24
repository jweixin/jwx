package com.github.jweixin.jwx.message.annotation;

/**
 * 菜单事件类型枚举
 * @author Administrator
 *
 */
public enum MenuType {
	PIC_PHOTO_OR_ALBUM("pic_photo_or_album"),
	PIC_SYSPHOTO("pic_sysphoto"),
	PIC_WEIXIN("pic_weixin"),
	SCANCODE_PUSH("scancode_push"),
	SCANCODE_WAITMSG("scancode_waitmsg"),
	LOCATION_SELECT("location_select");
	
	private String type;
	
	private MenuType(String type){
		this.type = type;
	}
	
	public String type(){
		return this.type;
	}

}
