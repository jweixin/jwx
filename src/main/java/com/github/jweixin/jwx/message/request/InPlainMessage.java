package com.github.jweixin.jwx.message.request;

public abstract class InPlainMessage extends InMessage {
	// 消息 id，64 位整型
	protected long msgId;

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

}
