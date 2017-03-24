package com.github.jweixin.jwx.message.annotation;

/**
 * 版权事件推送枚举
 * @author Administrator
 *
 */
public enum Copyright {
	QUALIFICATION_VERIFY_SUCCESS("qualification_verify_success"),
	QUALIFICATION_VERIFY_FAIL("qualification_verify_fail"),
	NAMING_VERIFY_SUCCESS("naming_verify_success"),
	NAMING_VERIFY_FAIL("naming_verify_fail"),
	ANNUAL_RENEW("annual_renew"),
	VERIFY_EXPIRED("verify_expired");
	
	private String type;
	
	private Copyright(String type){
		this.type = type;
	}
	
	public String type(){
		return this.type;
	}

}
