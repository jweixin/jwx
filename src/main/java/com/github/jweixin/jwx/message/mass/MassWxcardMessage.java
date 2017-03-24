package com.github.jweixin.jwx.message.mass;

import java.util.List;

/**
 * 群发卡券消息
 * @author Administrator
 *
 */
public class MassWxcardMessage extends MassMessage {
	/**
	 * 卡券
	 */
	private Wxcard wxcard;
	
	public MassWxcardMessage() {
		super();
		this.msgtype = "wxcard";
	}
	
	public MassWxcardMessage(Filter filter) {
		super(filter);
		this.msgtype = "wxcard";
	}
	
	public MassWxcardMessage(Filter filter, String cardId) {
		super(filter);
		this.msgtype = "wxcard";
		this.wxcard = new Wxcard(cardId);
	}
	
	public MassWxcardMessage(List<String> touser) {
		super(touser);
		this.msgtype = "wxcard";
	}
	
	public MassWxcardMessage(List<String> touser, String cardId) {
		super(touser);
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
