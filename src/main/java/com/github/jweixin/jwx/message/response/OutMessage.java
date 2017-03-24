package com.github.jweixin.jwx.message.response;

import java.util.Date;

import com.github.jweixin.jwx.message.request.InMessage;

public abstract class OutMessage {
	// 接收方帐号（收到的 OpenID）
	protected String toUserName;
	// 开发者微信号
	protected String fromUserName;
	// 消息创建时间 （整型）
	protected long createTime;
	// 消息类型（text/music/news/voice/video/image）
	protected String msgType;
	
	public OutMessage() {
		this.createTime = new Date().getTime();
	}

	public OutMessage(InMessage inMessage) {
		this.toUserName = inMessage.getFromUserName();
		this.fromUserName = inMessage.getToUserName();
		this.createTime = new Date().getTime();
	}

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

	protected String nullToBlank(String str) {
		return null == str ? "" : str;
	}

	protected abstract void subXml(StringBuilder sb);

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>\n");
		sb.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>\n");
		sb.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>\n");
		sb.append("<CreateTime>").append(createTime).append("</CreateTime>\n");
		sb.append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>\n");
		subXml(sb);
		sb.append("</xml>");

		return sb.toString();
	}

}
