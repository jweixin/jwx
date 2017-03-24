package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 系统拍照发图
 * 弹出系统拍照发图用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，
 * 并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。
 * @author Administrator
 *
 */
public class PicSysphotoButton extends Button {
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	private String key;

	public PicSysphotoButton(String name) {
		super(name);
		this.type = "pic_sysphoto";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
