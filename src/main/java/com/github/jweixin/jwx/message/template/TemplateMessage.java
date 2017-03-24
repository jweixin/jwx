package com.github.jweixin.jwx.message.template;

import java.util.Map;

import com.github.jweixin.jwx.message.request.InMessage;

/**
 * 模板消息
 * 
 * @author Administrator
 *
 */
public class TemplateMessage {
	/**
	 * 接收者openid
	 */
	private String touser;
	/**
	 * 模板ID
	 */
	private String templateId;
	/**
	 * 模板跳转链接
	 */
	private String url;
	/**
	 * 模板数据
	 */
	private Map<String, TemplateData> data;
	
	public TemplateMessage() {
	}
	
	public TemplateMessage(InMessage in) {
		this.touser = in.getFromUserName();
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, TemplateData> getData() {
		return data;
	}

	public void setData(Map<String, TemplateData> data) {
		this.data = data;
	}

}
