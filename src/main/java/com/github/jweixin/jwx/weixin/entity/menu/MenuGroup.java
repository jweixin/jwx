package com.github.jweixin.jwx.weixin.entity.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单列表 有个性化菜单时查询返回
 * 
 * @author Administrator
 *
 */
public class MenuGroup extends AbstractMenu {
	/**
	 * 菜单
	 */
	private Menu menu;
	/**
	 * 个性化菜单列表
	 */
	private List<ConditionalMenu> conditionalmenu = new ArrayList<ConditionalMenu>();

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<ConditionalMenu> getConditionalmenu() {
		return conditionalmenu;
	}

	public void setConditionalmenu(List<ConditionalMenu> conditionalmenu) {
		this.conditionalmenu = conditionalmenu;
	}

}
