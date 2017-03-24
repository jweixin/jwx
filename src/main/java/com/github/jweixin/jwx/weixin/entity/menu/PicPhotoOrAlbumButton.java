package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 拍照或者相册发图
 * 弹出拍照或者相册发图用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。
 * @author Administrator
 *
 */
public class PicPhotoOrAlbumButton extends Button {
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	private String key;

	public PicPhotoOrAlbumButton(String name) {
		super(name);
		this.type = "pic_photo_or_album";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
