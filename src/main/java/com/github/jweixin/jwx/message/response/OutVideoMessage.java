package com.github.jweixin.jwx.message.response;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 回复视频消息
 * @author Administrator
 *
 */
public class OutVideoMessage extends OutMessage {
	// 视频
	private Video video;
	
	public OutVideoMessage(InMessage inMessage) {
		super(inMessage);
		this.msgType = "video";
	}
	
	public OutVideoMessage(InMessage inMessage, Video video) {
		super(inMessage);
		this.msgType = "video";
		this.video = video;
	}
	
	public OutVideoMessage() {
		super();
		this.msgType = "video";
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public String getMsgType() {
		return msgType;
	}

	@Override
	protected void subXml(StringBuilder sb) {
		if (video == null || video.getMediaId() == null) {
			throw new NullPointerException("响应的视频消息mediaId不能为空");
		}
		sb.append("<Video>\n");
		sb.append("<MediaId><![CDATA[").append(video.getMediaId()).append("]]></MediaId>\n");
		sb.append("<Title><![CDATA[").append(nullToBlank(video.getTitle())).append("]]></Title>\n");
		sb.append("<Description><![CDATA[").append(nullToBlank(video.getDescription())).append("]]></Description>\n");
		sb.append("</Video>\n");
	}

}
