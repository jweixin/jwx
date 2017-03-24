package com.github.jweixin.jwx.message.mass;

import java.util.List;

import com.github.jweixin.jwx.message.response.Image;

/**
 * 群发图片消息
 * @author Administrator
 *
 */
public class MassImageMessage extends MassMessage {
	/**
	 * 图片
	 */
	private Image image;
	
	public MassImageMessage() {
		super();
		this.msgtype = "image";
	}
	
	public MassImageMessage(Filter filter) {
		super(filter);
		this.msgtype = "image";
	}
	
	public MassImageMessage(Filter filter, String mediaId) {
		super(filter);
		this.msgtype = "image";
		this.image = new Image(mediaId);
	}
	
	public MassImageMessage(List<String> touser) {
		super(touser);
		this.msgtype = "image";
	}
	
	public MassImageMessage(List<String> touser, String mediaId) {
		super(touser);
		this.msgtype = "image";
		this.image = new Image(mediaId);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
