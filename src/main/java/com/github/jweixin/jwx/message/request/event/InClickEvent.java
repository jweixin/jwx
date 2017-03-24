package com.github.jweixin.jwx.message.request.event;

/**
 * 自定义菜单事件 
 * 用户点击自定义菜单后，微信会把点击事件推送给开发者，请注意，点击菜单弹出子菜单，不会产生上报。 
 * 点击菜单拉取消息时的事件推送
 * 
 * @author Administrator
 *
 */
public class InClickEvent extends InEvent {
	// 事件类型，CLICK
	final private String event = "CLICK";
	// 事件KEY值，与自定义菜单接口中KEY值对应
	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getEvent() {
		return event;
	}

}
