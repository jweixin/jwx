package com.github.jweixin.jwx.message.response;

/**
 * success枚举类型
 * @author Administrator
 *
 */
public enum Success {
	/**
	 * 唯一的对象，表示返回"success"字符串
	 */
	SUCCESS;
	
	private Success(){
	}
	
	public String value(){
		return "success";
	}
}
