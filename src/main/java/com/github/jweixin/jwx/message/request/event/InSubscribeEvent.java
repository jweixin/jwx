package com.github.jweixin.jwx.message.request.event;

/*
 * 关注事件
 */
public class InSubscribeEvent extends InEvent {
	// 事件类型，subscribe(订阅)
	final private String event = "subscribe";

	public String getEvent() {
		return event;
	}

}
