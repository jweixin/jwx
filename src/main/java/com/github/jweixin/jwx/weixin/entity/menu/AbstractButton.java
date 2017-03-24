package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 抽象按钮
 * @author Administrator
 *
 */
public abstract class AbstractButton {
	//菜单标题，不超过16个字节，子菜单不超过60个字节
	protected String name;

	public AbstractButton(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
