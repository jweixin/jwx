package com.github.jweixin.jwx.message.custom;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 发送卡券
 * @author Administrator
 *
 */
public class CustomWxcardMessage extends CustomMessage {
	/**
	 * 卡券
	 */
	private Wxcard wxcard;
	
	public CustomWxcardMessage() {
		super();
		this.msgtype = "wxcard";
	}

	public CustomWxcardMessage(InMessage inMessage) {
		super(inMessage);
		this.msgtype = "wxcard";
	}

	public CustomWxcardMessage(InMessage inMessage, Wxcard wxcard) {
		super(inMessage);
		this.msgtype = "wxcard";
		this.wxcard = wxcard;
	}
	
	public CustomWxcardMessage(InMessage inMessage, String cardId) {
		super(inMessage);
		this.msgtype = "wxcard";
		this.wxcard = new Wxcard(cardId);
	}

	public Wxcard getWxcard() {
		return wxcard;
	}

	public void setWxcard(Wxcard wxcard) {
		this.wxcard = wxcard;
	}

}
