package com.github.jweixin.jwx.message.response;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 回复图片消息
 * @author Administrator
 *
 */
public class OutImageMessage extends OutMessage {
	private Image image;
	
	public OutImageMessage(InMessage inMessage) {
		super(inMessage);
		this.msgType = "image";
	}
	
	public OutImageMessage(InMessage inMessage, Image image) {
		super(inMessage);
		this.msgType = "image";
		this.image = image;
	}
	
	public OutImageMessage() {
		super();
		this.msgType = "image";
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getMsgType() {
		return msgType;
	}

	@Override
	protected void subXml(StringBuilder sb) {
		if (image == null || null == image.getMediaId()) {
			throw new NullPointerException("响应的图片消息mediaId不能为空");
		}
		sb.append("<Image>\n");
		sb.append("<MediaId><![CDATA[").append(image.getMediaId()).append("]]></MediaId>\n");
		sb.append("</Image>\n");
	}

}
