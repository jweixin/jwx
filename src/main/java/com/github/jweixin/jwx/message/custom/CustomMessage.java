package com.github.jweixin.jwx.message.custom;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 客服消息抽象类
 * @author Administrator
 *
 */
public abstract class CustomMessage {
	/**
	 * 普通用户openid
	 */
	private String touser;
	/**
	 * 消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息（点击跳转到外链）为news，图文消息（点击跳转到图文消息页面）为mpnews，卡券为wxcard
	 */
	protected String msgtype;
	
	/**
	 * 客服服务账号
	 */
	protected Customservice customservice;
	
	public CustomMessage() {
	}
	
	public CustomMessage(InMessage in) {
		this.touser = in.getFromUserName();
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Customservice getCustomservice() {
		return customservice;
	}

	public void setCustomservice(Customservice customservice) {
		this.customservice = customservice;
	}

}
