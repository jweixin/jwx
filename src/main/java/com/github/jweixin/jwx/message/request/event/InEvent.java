package com.github.jweixin.jwx.message.request.event;

import com.github.jweixin.jwx.message.request.InMessage;

public abstract class InEvent extends InMessage {
	// 消息类型（event）
	final protected String msgType = "event";
	// 事件类型
	protected String event;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getMsgType() {
		return msgType;
	}

}
