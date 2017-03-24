package com.github.jweixin.jwx.message.template;

import com.github.jweixin.jwx.weixin.entity.ReturnCode;

/**
 * 模板消息返回码
 * @author Administrator
 *
 */
public class TemplateMessageReturnCode extends ReturnCode {
	/**
	 * 消息id
	 */
	private long msgid;

	public long getMsgid() {
		return msgid;
	}

	public void setMsgid(long msgid) {
		this.msgid = msgid;
	}

}
