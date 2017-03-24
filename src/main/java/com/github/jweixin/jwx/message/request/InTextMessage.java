package com.github.jweixin.jwx.message.request;

public class InTextMessage extends InPlainMessage {
	// 消息类型
	private final String msgType = "text";
	// 文本消息内容
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgType() {
		return msgType;
	}

}
