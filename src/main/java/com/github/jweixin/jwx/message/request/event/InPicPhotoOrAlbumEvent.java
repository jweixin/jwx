package com.github.jweixin.jwx.message.request.event;

/**
 * 弹出拍照或者相册发图的事件推送
 * 
 * @author Administrator
 *
 */
public class InPicPhotoOrAlbumEvent extends InMenuEvent {
	// 事件类型，pic_photo_or_album
	final private String event = "pic_photo_or_album";
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
