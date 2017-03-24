package com.github.jweixin.jwx.message.mass;

/**
 * 群发消息发送状态
 * 
 * @author Administrator
 *
 */
public class MsgStatus {
	/**
	 * 群发消息后返回的消息id
	 */
	private long msgId;
	/**
	 * 消息发送后的状态，SEND_SUCCESS表示发送成功
	 */
	private String msgStatus;

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

}
