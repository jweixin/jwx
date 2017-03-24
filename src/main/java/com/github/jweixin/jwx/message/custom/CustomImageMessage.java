package com.github.jweixin.jwx.message.custom;

import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.response.Image;

public class CustomImageMessage extends CustomMessage {
	
	private Image image;
	
	public CustomImageMessage() {
		super();
		this.msgtype = "image";
	}

	public CustomImageMessage(InMessage inMessage) {
		super(inMessage);
		this.msgtype = "image";
	}

	public CustomImageMessage(InMessage inMessage, Image image) {
		super(inMessage);
		this.msgtype = "image";
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
