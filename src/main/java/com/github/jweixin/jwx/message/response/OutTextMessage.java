package com.github.jweixin.jwx.message.response;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 回复文本消息
 * @author Administrator
 *
 */
public class OutTextMessage extends OutMessage {
	private Text text;
	
	public OutTextMessage() {
		super();
		this.msgType = "text";
	}
	
	public OutTextMessage(InMessage inMessage) {
		super(inMessage);
		this.msgType = "text";
	}
	
	public OutTextMessage(InMessage inMessage, String content) {
		super(inMessage);
		this.msgType = "text";
		this.text = new Text(content);
	}
	
	public OutTextMessage(InMessage inMessage, Text text) {
		super(inMessage);
		this.msgType = "text";
		this.text = text;
	}

	public String getMsgType() {
		return msgType;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	@Override
	protected void subXml(StringBuilder sb) {
		if (null == text || text.getContent() == null) {
			throw new NullPointerException("响应的文本消息内容不能为空");
		}
		sb.append("<Content><![CDATA[").append(text.getContent()).append("]]></Content>\n");
	}

}