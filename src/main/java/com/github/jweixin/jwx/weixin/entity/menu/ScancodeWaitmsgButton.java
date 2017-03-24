package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 扫码带提示按钮
 * 扫码推事件且弹出“消息接收中”提示框用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，
 * 将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
 * @author Administrator
 *
 */
public class ScancodeWaitmsgButton extends Button {
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	private String key;

	public ScancodeWaitmsgButton(String name) {
		super(name);
		this.type = "scancode_waitmsg";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
