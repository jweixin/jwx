package com.github.jweixin.jwx.message.request.event;

/**
 * 菜单事件抽象类
 * @author Administrator
 *
 */
public abstract class InMenuEvent extends InEvent {
	/**
	 * 事件KEY值，由开发者在创建菜单时设定
	 */
	protected String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}
