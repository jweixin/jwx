package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 扫码推事件
 * 扫码推事件用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），
 * 且会将扫码的结果传给开发者，开发者可以下发消息。
 * @author Administrator
 *
 */
public class ScancodePushButton extends Button {
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	private String key;

	public ScancodePushButton(String name) {
		super(name);
		this.type = "scancode_push";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
