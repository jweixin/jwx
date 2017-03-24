package com.github.jweixin.jwx.message.request.event;

/*
 * 取消关注事件
 */
public class InUnsubscribeEvent extends InEvent {
	// 事件类型，unsubscribe(取消订阅)
	final private String event = "unsubscribe";

	public String getEvent() {
		return event;
	}

}
