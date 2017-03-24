package com.github.jweixin.jwx.message.mass;

import java.util.List;

/**
 * 图文消息
 * 
 * @author Administrator
 *
 */
public class MassMpnewsMessage extends MassMessage {
	/**
	 * 用于设定即将发送的图文消息
	 */
	private Mpnews mpnews;
	/**
	 * 当 send_ignore_reprint 参数设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。 当
	 * send_ignore_reprint 参数设置为0时，文章被判定为转载时，将停止群发操作。 send_ignore_reprint 默认为0。
	 */
	private int sendIgnoreReprint = 0;
	
	public MassMpnewsMessage() {
		super();
		this.msgtype = "mpnews";
	}
	
	public MassMpnewsMessage(Filter filter) {
		super(filter);
		this.msgtype = "mpnews";
	}
	
	public MassMpnewsMessage(Filter filter, String mediaId) {
		super(filter);
		this.msgtype = "mpnews";
		this.mpnews = new Mpnews(mediaId);
	}
	
	public MassMpnewsMessage(List<String> touser) {
		super(touser);
		this.msgtype = "mpnews";
	}
	
	public MassMpnewsMessage(List<String> touser, String mediaId) {
		super(touser);
		this.msgtype = "mpnews";
		this.mpnews = new Mpnews(mediaId);
	}

	public Mpnews getMpnews() {
		return mpnews;
	}

	public void setMpnews(Mpnews mpnews) {
		this.mpnews = mpnews;
	}

	public int getSendIgnoreReprint() {
		return sendIgnoreReprint;
	}

	public void setSendIgnoreReprint(int sendIgnoreReprint) {
		this.sendIgnoreReprint = sendIgnoreReprint;
	}

}
