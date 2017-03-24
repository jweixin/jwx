package com.github.jweixin.jwx.message.request;

public abstract class InMessage {
	// 开发者微信号
	protected String toUserName;
	// 发送方帐号（一个 OpenID）
	protected String fromUserName;
	// 消息创建时间 （整型）
	protected long createTime;
	// 消息类型（text/image/location/link）
	protected String msgType;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
