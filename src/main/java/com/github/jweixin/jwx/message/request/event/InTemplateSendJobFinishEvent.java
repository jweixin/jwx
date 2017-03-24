package com.github.jweixin.jwx.message.request.event;

/**
 * 模板消息事件推送
 * @author Administrator
 *
 */
public class InTemplateSendJobFinishEvent extends InEvent {
	// 事件类型，TEMPLATESENDJOBFINISH
	final private String event = "TEMPLATESENDJOBFINISH";
	/**
	 * 消息id
	 */
	private long msgId;
	/**
	 * 发送状态
	 * success -> 发送状态为成功
	 * failed:user block -> 发送状态为用户拒绝接收
	 * failed: system failed -> 发送状态为发送失败（非用户拒绝）
	 */
	private String status;

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEvent() {
		return event;
	}
}
