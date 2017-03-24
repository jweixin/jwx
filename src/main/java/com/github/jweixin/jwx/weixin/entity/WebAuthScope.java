package com.github.jweixin.jwx.weixin.entity;

/**
 * 微信授权作用域
 * @author Administrator
 *
 */
public enum WebAuthScope {
	BASE("snsapi_base"), USERINFO("snsapi_userinfo");
	
	private String type;
	
	private WebAuthScope(String type){
		this.type = type;
	}
	
	public String type(){
		return this.type;
	}

}
