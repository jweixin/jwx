package com.github.jweixin.jwx.weixin.entity.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 子菜单
 * @author Administrator
 *
 */
public class SubMenu extends AbstractButton {
	/**
	 * 二级菜单数组，个数应为1~5个
	 */
	private List<Button> subButton = new ArrayList<Button>();

	public SubMenu(String name) {
		super(name);
	}

	public List<Button> getSubButton() {
		return subButton;
	}

	public void setSubButton(List<Button> subButton) {
		this.subButton = subButton;
	}

}
