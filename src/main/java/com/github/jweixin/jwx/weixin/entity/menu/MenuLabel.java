package com.github.jweixin.jwx.weixin.entity.menu;

/**
 * 默认菜单，无个性化菜单
 * @author Administrator
 *
 */
public class MenuLabel extends AbstractMenu {
	/**
	 * 菜单
	 */
	private Menu menu;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
