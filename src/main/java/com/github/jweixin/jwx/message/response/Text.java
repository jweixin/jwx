package com.github.jweixin.jwx.message.response;

/**
 * 文本
 * @author Administrator
 *
 */
public class Text {
	/**
	 * 文本内容
	 */
	private String content;
	
	public Text() {
	}
	
	public Text(String content){
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
