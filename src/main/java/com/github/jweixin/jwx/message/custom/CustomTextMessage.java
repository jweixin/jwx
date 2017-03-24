package com.github.jweixin.jwx.message.custom;

import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.response.Text;

/**
 * 客服文本消息
 * @author Administrator
 *
 */
public class CustomTextMessage extends CustomMessage {

	private Text text;

	public CustomTextMessage() {
		super();
		this.msgtype = "text";
	}

	public CustomTextMessage(InMessage inMessage) {
		super(inMessage);
		this.msgtype = "text";
	}

	public CustomTextMessage(InMessage inMessage, String content) {
		super(inMessage);
		this.msgtype = "text";
		this.text = new Text(content);
	}
	
	public CustomTextMessage(InMessage inMessage, Text text) {
		super(inMessage);
		this.msgtype = "text";
		this.text = text;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}
