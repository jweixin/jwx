package com.github.jweixin.jwx.message.request.event;

/**
 * 用户已关注时的事件推送 
 * 如果用户已经关注公众号，则微信会将带场景值扫描事件推送给开发者。
 * 
 * @author Administrator
 *
 */
public class InScanEvent extends InEvent {
	// 事件类型，SCAN
	final private String event = "SCAN";
	// 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	private String eventKey;
	// 二维码的ticket，可用来换取二维码图片
	private String ticket;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getEvent() {
		return event;
	}

}
