package com.github.jweixin.jwx.weixin.entity.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 微信菜单
 * 
 * @author Administrator
 *
 */
public class Menu {
	/**
	 * 一级菜单数组，个数应为1~3个
	 */
	protected List<AbstractButton> button = new ArrayList<AbstractButton>();
	/**
	 * 菜单id
	 */
	protected String menuid;

	/**
	 * 检查微信菜单是否合法
	 * 
	 * @return
	 */
	public boolean check() {
		if (button == null || button.size() < 1 || button.size() > 3) {
			return false;
		} else {
			Iterator<AbstractButton> it = button.iterator();
			while (it.hasNext()) {
				AbstractButton sub = it.next();
				if (sub instanceof SubMenu) {
					SubMenu sm = (SubMenu) sub;
					if (sm.getSubButton() == null || sm.getSubButton().size() < 1 || sm.getSubButton().size() > 5) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public List<AbstractButton> getButton() {
		return button;
	}

	public void setButton(List<AbstractButton> button) {
		this.button = button;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	
}
