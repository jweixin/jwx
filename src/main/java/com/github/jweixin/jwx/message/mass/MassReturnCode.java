package com.github.jweixin.jwx.message.mass;

import com.github.jweixin.jwx.weixin.entity.ReturnCode;

/**
 * 消息群发返回码
 * 
 * @author Administrator
 *
 */
public class MassReturnCode extends ReturnCode {
	/**
	 * 消息发送任务的ID
	 */
	private long msgId;
	/**
	 * 消息的数据ID，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，
	 * 获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分， 详见图文分析数据接口中的msgid字段的介绍。
	 */
	private Long msgDataId;

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public Long getMsgDataId() {
		return msgDataId;
	}

	public void setMsgDataId(Long msgDataId) {
		this.msgDataId = msgDataId;
	}

}
