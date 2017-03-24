package com.github.jweixin.jwx.message.request.event;

/**
 * 弹出微信相册发图器的事件推送
 * 
 * @author Administrator
 *
 */
public class InPicWeixinEvent extends InMenuEvent {
	// 事件类型，pic_weixin
	final private String event = "pic_weixin";
	// 发送的图片信息
	private SendPicsInfo sendPicsInfo;

	public SendPicsInfo getSendPicsInfo() {
		return sendPicsInfo;
	}

	public void setSendPicsInfo(SendPicsInfo sendPicsInfo) {
		this.sendPicsInfo = sendPicsInfo;
	}

	public String getEvent() {
		return event;
	}

}
