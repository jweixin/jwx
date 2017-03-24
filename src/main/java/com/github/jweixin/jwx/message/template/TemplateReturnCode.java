package com.github.jweixin.jwx.message.template;

import com.github.jweixin.jwx.weixin.entity.ReturnCode;

/**
 * 模板返回码
 * @author Administrator
 *
 */
public class TemplateReturnCode extends ReturnCode {
	/**
	 * 模板id
	 */
	private String templateId;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
