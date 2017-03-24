package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 菜单按钮
 * 
 * @author Administrator
 *
 */
public abstract class Button extends AbstractButton {
	// 菜单的响应动作类型
	protected String type;

	public Button(String name) {
		super(name);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
