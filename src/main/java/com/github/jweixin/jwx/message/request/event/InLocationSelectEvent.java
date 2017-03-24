package com.github.jweixin.jwx.message.request.event;

/**
 * 弹出地理位置选择器的事件推送
 * 
 * @author Administrator
 *
 */
public class InLocationSelectEvent extends InMenuEvent {
	// 事件类型，location_select
	final private String event = "location_select";
	// 发送的位置信息
	private SendLocationInfo sendLocationInfo;

	public SendLocationInfo getSendLocationInfo() {
		return sendLocationInfo;
	}

	public void setSendLocationInfo(SendLocationInfo sendLocationInfo) {
		this.sendLocationInfo = sendLocationInfo;
	}

	public String getEvent() {
		return event;
	}

}
