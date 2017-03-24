package com.github.jweixin.jwx.weixin.entity;

/**
 * 微信全局返回码
 * @author Administrator
 *
 */
public class ReturnCode {
	
	public final static int SC_OK = 0;

	protected int errcode;
	protected String errmsg;
	
	public ReturnCode() {
	}
	
	public ReturnCode(int errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}
	
	/**
	 * 验证微信接口返回对象是否有错误
	 * @return
	 */
	public boolean check(){
		if(errcode!=SC_OK){
			return false;
		}
		return true;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
