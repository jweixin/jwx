package com.github.jweixin.jwx.message.custom;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 发送图文消息（点击跳转到图文消息页面）
 * @author Administrator
 *
 */
public class CustomMpnewsMessage extends CustomMessage {
	
	private Mpnews mpnews;
	
	public CustomMpnewsMessage() {
		super();
		this.msgtype = "mpnews";
	}

	public CustomMpnewsMessage(InMessage inMessage) {
		super(inMessage);
		this.msgtype = "mpnews";
	}

	public CustomMpnewsMessage(InMessage inMessage, Mpnews mpnews) {
		super(inMessage);
		this.msgtype = "mpnews";
		this.mpnews = mpnews;
	}
	
	public CustomMpnewsMessage(InMessage inMessage, String mediaId) {
		super(inMessage);
		this.msgtype = "mpnews";
		this.mpnews = new Mpnews(mediaId);
	}

	public Mpnews getMpnews() {
		return mpnews;
	}

	public void setMpnews(Mpnews mpnews) {
		this.mpnews = mpnews;
	}

}
