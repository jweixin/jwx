package com.github.jweixin.jwx.message.request.event;

/**
 * 用户未关注时，进行关注后的事件推送
 * 如果用户还未关注公众号，则用户可以关注公众号，关注后微信会将带场景值关注事件推送给开发者。
 * @author Administrator
 */
public class InScanSubscribeEvent extends InEvent {
	// 事件类型，subscribe
	final private String event = "subscribe";
	// 事件KEY值，qrscene_为前缀，后面为二维码的参数值
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
