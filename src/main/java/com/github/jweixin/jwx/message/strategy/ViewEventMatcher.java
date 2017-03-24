package com.github.jweixin.jwx.message.strategy;

import com.github.jweixin.jwx.context.MsgMatcher;

/**
 * 点击菜单跳转链接事件适配器
 * @author Administrator
 *
 */
public class ViewEventMatcher extends MsgMatcher {
	/**
	 * 存储ViewEvent事件注解的menuId值
	 */
	private Long menuId;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}
