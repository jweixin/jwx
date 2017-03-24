package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * click类型按钮
 * @author Administrator
 *
 */
public class ClickButton extends Button {
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	private String key;

	public ClickButton(String name) {
		super(name);
		this.type = "click";
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
