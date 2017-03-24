package com.github.jweixin.jwx.message.custom;

import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.response.Video;

public class CustomVideoMessage extends CustomMessage {
	
	private Video video;
	
	public CustomVideoMessage() {
		super();
		this.msgtype = "video";
	}

	public CustomVideoMessage(InMessage inMessage) {
		super(inMessage);
		this.msgtype = "video";
	}

	public CustomVideoMessage(InMessage inMessage, Video video) {
		super(inMessage);
		this.msgtype = "video";
		this.video = video;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
