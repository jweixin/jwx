package com.github.jweixin.jwx.message.mass;

import java.util.List;

import com.github.jweixin.jwx.message.response.Text;

/**
 * 群发文本消息
 * @author Administrator
 *
 */
public class MassTextMessage extends MassMessage {
	/**
	 * 文本
	 */
	private Text text;
	
	public MassTextMessage() {
		super();
		this.msgtype = "text";
	}
	
	public MassTextMessage(Filter filter) {
		super(filter);
		this.msgtype = "text";
	}
	
	public MassTextMessage(Filter filter, String content) {
		super(filter);
		this.msgtype = "text";
		this.text = new Text(content);
	}
	
	public MassTextMessage(List<String> touser) {
		super(touser);
		this.msgtype = "text";
	}
	
	public MassTextMessage(List<String> touser, String content) {
		super(touser);
		this.msgtype = "text";
		this.text = new Text(content);
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}
