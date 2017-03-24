package com.github.jweixin.jwx.message.request.event;

/**
 * 点击菜单跳转链接时的事件推送
 * 
 * @author Administrator
 *
 */
public class InViewEvent extends InEvent {
	// 事件类型，VIEW
	final private String event = "VIEW";
	// 指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了。
	/**
	 * 事件KEY值，由开发者在创建菜单时设定
	 */
	private String eventKey;
	private Long menuId;

	public String getEvent() {
		return event;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

}
