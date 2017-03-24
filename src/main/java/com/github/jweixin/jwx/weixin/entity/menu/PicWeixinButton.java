package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 微信相册发图
 * 弹出微信相册发图器用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，
 * 并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
 * @author Administrator
 *
 */
public class PicWeixinButton extends Button {
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	private String key;

	public PicWeixinButton(String name) {
		super(name);
		this.type = "pic_weixin";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
