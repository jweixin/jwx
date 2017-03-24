package com.github.jweixin.jwx.message.request.event;

/**
 * 弹出系统拍照发图的事件推送
 * 
 * @author Administrator
 *
 */
public class InPicSysphotoEvent extends InMenuEvent {
	// 事件类型，pic_sysphoto
	final private String event = "pic_sysphoto";
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
