package com.github.jweixin.jwx.message.mass;

import java.util.List;

/**
 * 群发消息抽象类
 * 
 * @author Administrator
 *
 */
public abstract class MassMessage {
	/**
	 * 群发的消息类型，图文消息为mpnews，文本消息为text，语音为voice，音乐为music，图片为image，视频为video，
	 * 卡券为wxcard
	 */
	protected String msgtype;

	private Filter filter;

	private List<String> touser;

	public MassMessage() {
	}

	public MassMessage(Filter filter) {
		this.filter = filter;
	}
	
	public MassMessage(List<String> touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public List<String> getTouser() {
		return touser;
	}

	public void setTouser(List<String> touser) {
		this.touser = touser;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}
}
