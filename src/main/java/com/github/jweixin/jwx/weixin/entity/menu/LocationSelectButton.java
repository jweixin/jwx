package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 发送位置
 * 弹出地理位置选择器用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，
 * 同时收起位置选择工具，随后可能会收到开发者下发的消息。
 * @author Administrator
 *
 */
public class LocationSelectButton extends Button {
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	private String key;

	public LocationSelectButton(String name) {
		super(name);
		this.type = "location_select";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
