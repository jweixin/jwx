package com.github.jweixin.jwx.message.template;

/**
 * 模板数据
 * 
 * @author Administrator
 *
 */
public class TemplateData {
	/**
	 * 值
	 */
	private String value;
	/**
	 * 颜色
	 */
	private String color;

	public TemplateData() {
	}

	public TemplateData(String value) {
		this.value = value;
	}

	public TemplateData(String value, String color) {
		this.value = value;
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
